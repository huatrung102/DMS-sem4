define(['plugins/router', 'plugins/http', 'toastr', 'uuid', 'knockout', 'kinetic', 'ma', 'linq'], function (router, http, toastr, uuid, ko) {
    Type.registerNamespace("BPM");
    //------------------------------------------------------------
    BPM.Base = function () {
        BPM.Base.initializeBase(this);
    }
    BPM.Base.prototype = {
        initialize: function () {
        },
        dispose: function () {
        },
        get_events: function () {
            if (!this._events) {
                this._events = new Sys.EventHandlerList();
            }
            return this._events;
        },
        _raiseEvent: function (eventName, eventArgs) {
            var handler = this.get_events().getHandler(eventName);
            if (handler) {
                if (!eventArgs) eventArgs = Sys.EventArgs.Empty;
                handler(this, eventArgs);
            }
        },
    }
    BPM.Base.registerClass("BPM.Base");
    //------------------------------------------------------------
    BPM.Diagram = function (config) {
        BPM.Diagram.initializeBase(this);
        this._element = config.element;
        this._width = config.width;
        this._height = config.height;
        this._phaseTitleHeight = 50;
        this._padding = 12;
        this._stage = new Kinetic.Stage({
            container: config.element,
            width: config.width,
            height: config.height
        });

        this._layer0 = new Kinetic.Layer();
        this._stage.add(this._layer0);
        this._layer1 = new Kinetic.Layer();
        this._stage.add(this._layer1);
        this._layer2 = new Kinetic.Layer();
        this._stage.add(this._layer2);
        this._layer3 = new Kinetic.Layer();
        this._stage.add(this._layer3);
        this._layer0.setZIndex(10);
        this._layer1.setZIndex(20);
        this._layer2.setZIndex(30);
        this._layer3.setZIndex(40);
        this._phases = [];
        this._layers = [];
        this._stages = [];
        this._connector = new BPM.Connector({ diagram: this });
    }
    BPM.Diagram.prototype = {
        selectedItem: function () {
            return this._resizer._item;
        },
        addPhase: function (config) {
            if (!config) {
                var length = this._phases.length;
                //init config default
                //x, y, width, height, name
                config = {
                    width: 200,
                    height: 500,
                    name: "Phase " + length,
                    id: uuid.v4()
                }
                if (length > 0) {
                    var lastPhase = this._phases[length - 1];
                    var pos = lastPhase.getNextPos();
                    config.x = pos.x, config.y = pos.y;
                    config.width = lastPhase._shape.width();
                    config.height = lastPhase._shape.height();
                } else {
                    config.x = this._padding, config.y = this._padding;
                }
            }
            var phase = new BPM.Phase($.extend(config, { diagram: this }));
            this._phases.push(phase);
            this.refreshLayers();
            this._layer0.draw();
        },
        addPhases: function (phases) {
            var self = this;
            $(phases).each(function (idx, p) {
                var phase = new BPM.Phase($.extend(p, { diagram: self }));
                self._phases.push(phase);
            });
        },
        addLayers: function (layers) {
            var self = this;
            $(layers).each(function (idx, l) {
                var layer = new BPM.Layer($.extend(l, { diagram: self }));
                self._layers.push(layer);
            });
        },
        addStages: function (stages) {
            var self = this;
            $(stages).each(function (idx, s) {
                var stage = new BPM.Stage($.extend(s, { diagram: self }));
                self._stages.push(stage);
            });
        },
        addLayer: function (config) {
            if (!config) {
                //init config default
                //x, y, width, height, name
                config = {
                    x: this._padding,
                    height: 100,
                    id: uuid.v4(),
                    group: BPM.Positions[0].Id
                }
                config.y = this._phaseTitleHeight + this._padding + $.Enumerable.From(this._layers).Sum(function (x) {
                    return x._shape.height();
                });
                config.width = $.Enumerable.From(this._phases).Sum(function (x) {
                    return x._shape.width();
                });
            }
            var layer = new BPM.Layer($.extend(config, { diagram: this }));
            this._layers.push(layer);
            this.refreshPhases();
            this._layer0.draw();
        },
        addStage: function (config) {
            var self = this;
            if (!config) {
                config = {
                    x: this._padding * 2,
                    y: this._padding * 2 + this._phaseTitleHeight,
                    width: 100,
                    height: 100,
                    name: "",
                    id: uuid.v4(),
                    allowSendBack: true,
                    isLeader: false,
                    lookupCR: false,
                    endWorkflow: false,
                    duration: 0,
                    tasks: []
                }
                $(this._stages).each(function (idx, s) {
                    if (s._x == config.x && s._y == config.y) {
                        config.x += self._padding * 2;
                        config.y += self._padding * 2 + self._phaseTitleHeight;
                    } else
                        return false;
                });
            }
            var stage = new BPM.Stage($.extend(config, { diagram: this }));
            this._stages.push(stage);
            this._layer1.draw();

        },
        refreshLayers: function () {
            var totalWidth = $.Enumerable.From(this._phases).Sum(function (x) {
                return x._shape.width();
            });
            $(this._layers).each(function (idx1, l) {
                l.resize({ width: totalWidth, height: l._shape.height() });
            });
        },
        refreshPhases: function () {
            var totalHeight = this._phaseTitleHeight + $.Enumerable.From(this._layers).Sum(function (x) {
                return x._shape.height();
            });
            if (this._phases[0]._shape.height() < totalHeight) {
                $(this._phases).each(function (idx, p) {
                    p.resize({ width: p._shape.width(), height: totalHeight });
                });
            }
        },
        addSelectionChanged: function (handler) {
            this.get_events().addHandler("SelectionChange", handler);
        },
        onSelectionChanged: function () {
            this._raiseEvent("SelectionChange", {});
        },
        load: function (data) {
            var self = this;            
            this._stage.setWidth(data.width);
            this._stage.setHeight(data.height);
            this.addPhases(data.phases);
            this.addLayers(data.layers);
            this.addStages(data.stages);
            $(data.connections).each(function (idx, c) {
               try {
                   var begin = $.Enumerable.From(self._stages).Single(function(x) {
                       return x._id === c.begin;
                   });
                   var end = $.Enumerable.From(self._stages).Single(function(x) {
                       return x._id === c.end;
                   });
                   self._connector.addConnection({ begin: begin, end: end, name: c.name, rule: c.rule, points: c.points });
               } catch (ex) {
                   
               }
            });
            this._layer0.draw();
            this._layer1.draw();
            this._layer2.draw();
        },
        serialize: function () {
            var self = this;
            var diagram = { width: this._stage.width(), height: this._stage.height(), phases: [], layers: [], stages: [], connections: [] };
            $(this._phases).each(function (idx, p) {
                var pos = p._shape.getAbsolutePosition();
                var phase = { id: p._id, x: pos.x, y: pos.y, width: p._width, height: p._height, name: p._data.name() };
                diagram.phases.push(phase);
            });
            $(this._layers).each(function (idx, l) {
                var pos = l._shape.getAbsolutePosition();
                var layer = { x: pos.x, y: pos.y, width: l._shape.width(), height: l._shape.height(), group: l._data.group };
                diagram.layers.push(layer);
            });
            $(this._stages).each(function (idx, s) {
                var pos = s._shape.getAbsolutePosition();
                var data = s._data;
                var stage = {
                    x: pos.x,
                    y: pos.y,
                    width: s._shape.width(),
                    height: s._shape.height(),
                    id: data.id,
                    code: data.code,
                    name: data.name(),
                    allowSendBack: data.allowSendBack,
                    endWorkflow: data.endWorkflow,
                    isLeader: data.isLeader(),
                    lookupCR: data.lookupCR,
                    duration: data.duration,
                    tasks: $.Enumerable.From(s._data.tasks()).Select(function (item) {
                        return { id: item.id, name: item.name, forms: item.selectedForms(), files: item.selectedFiles() };
                    }).ToArray()
                }
                diagram.stages.push(stage);
            });
            for (var key in this._connector._connections) {
                $(this._connector._connections[key]).each(function (idx, c) {
                    diagram.connections.push({ begin: c._begin._id, end: c._end._id, name: c._begin._data.connections()[idx].name(), rule: c._begin._data.connections()[idx].rule, points: c._points });
                });
            }

            var contentData = { phases: [], connections: [] };
            $(this._phases).each(function (idx, p) {
                var phase = { id: p._id, name: p._data.name(), stages: [] }
                var stages = $.Enumerable.From(self._stages).Where(function (x) {
                    return self.belongShape(p._shape, x._shape);
                }).Select(function (x) {
                    return x._id;
                }).ToArray();
                var stage;
                $(stages).each(function (idx1, sid) {
                    stage = $.Enumerable.From(diagram.stages).Single(function (x) {
                        return x.id == sid;
                    });
                    var sh = $.Enumerable.From(self._stages).Single(function (x) {
                        return x._id == sid;
                    })._shape;
                    var ly = $.Enumerable.From(self._layers).Single(function (x) {
                        return self.belongShape(x._shape, sh);
                    });

                    phase.stages.push({ id: stage.id, name: stage.name, tasks: stage.tasks, allowSendBack: stage.allowSendBack, isLeader: stage.isLeader, endWorkflow: stage.endWorkflow, duration: stage.duration, lookupCR: stage.lookupCR, code: stage.code, PositionId: ly._data.group });
                });
                contentData.phases.push(phase);
            });
            contentData.connections = $.Enumerable.From(diagram.connections).Select(function (x) {
                return { begin: x.begin, end: x.end, name: x.name, rule: x.rule };
            }).ToArray();
            return { designData: diagram, contentData: contentData };
        },
        belongShape: function (parent, child) {
            var pp = parent.getAbsolutePosition();
            var cp = child.getAbsolutePosition();
            return cp.x > pp.x && cp.x < pp.x + parent.width() && cp.y > pp.y && cp.y < pp.y + parent.height();
        }
    }
    BPM.Diagram.registerClass("BPM.Diagram", BPM.Base);
    //------------------------------------------------------------
    BPM.Phase = function (config) {
        BPM.Phase.initializeBase(this);
        var self = this;
        this._diagram = config.diagram;
        this._id = config.id;
        this._x = config.x;
        this._y = config.y;
        this._width = config.width;
        this._height = config.height;
        this._padding = 5;
        this._titleHeight = this._diagram._phaseTitleHeight;

        this._layers = [];
        this._data = {
            id: config.id,
            name: ko.observable(config.name)
        }
        this.render();
        this._data.name.subscribe(function (value) {
            self._text.setText(value);
            self._diagram._layer0.draw();
        });
    }
    BPM.Phase.prototype = {
        template: function () {
            return "template-phase";
        },
        render: function () {
            var self = this;
            this._shape = new Kinetic.Group({
                x: this._x,
                y: this._y,
                width: this._width,
                height: this._height
            });
            this._bounder = new Kinetic.Rect({
                x: 0,
                y: 0,
                width: this._width,
                height: this._height,
                stroke: 'black',
                strokeWidth: 0.5
            });
            this._line = new Kinetic.Line({
                points: [0, this._titleHeight, this._width, this._titleHeight],
                stroke: "black",
                strokeWidth: 0.5
            });


            this._header = new Kinetic.Rect({
                x: 0,
                y: 0,
                width: this._width,
                height: this._titleHeight,
                strokeWidth: 0
            });

            this._text = new Kinetic.Text({
                x: this._padding,
                y: this._padding,
                width: this._width - this._padding * 2,
                text: this._data.name(),
                fontSize: 14,
                fontFamily: 'Calibri',
                fill: 'green',
                align: 'center'
            });

            this._shape.add(this._bounder);
            this._shape.add(this._line);
            this._shape.add(this._header);
            this._shape.add(this._text);
            this._diagram._layer0.add(this._shape);
        },
        move: function (pos) {
            this._shape.setX(pos.x);
            this._shape.setY(pos.y);
        },
        resize: function (size) {
            var width = size.width;
            var height = size.height;
            this._width = width;
            this._height = height;
            this._shape.setWidth(width);
            this._shape.setHeight(height);
            this._bounder.setWidth(width);
            this._bounder.setHeight(height);
            this._line.setPoints([0, this._titleHeight, this._width, this._titleHeight]);
            this._header.setWidth(width);
            this._text.setWidth(width - this._padding * 2);
            $(this._layers).each(function (idx, layer) {
                layer.resize({ width: width, height: layer._height });
            });
        },
        getNextPos: function () {
            var pos = this._shape.getAbsolutePosition();
            return { x: pos.x + this._shape.width(), y: pos.y };
        }
    }
    BPM.Phase.registerClass("BPM.Phase", BPM.Base);
    //--------------------------------------------------
    BPM.Layer = function (config) {
        BPM.Layer.initializeBase(this);
        var self = this;
        this._id = config.id;
        this._x = config.x;
        this._y = config.y;
        this._height = config.height;
        this._width = config.width;
        this._diagram = config.diagram;
        this._titleHeight = 50;
        this._data = {
            id: config.id,
            group: config.group
        }
        this.render();
    }
    BPM.Layer.prototype = {
        template: function () {
            return "template-layer";
        },
        render: function () {
            var self = this;
            var width = self._width;
            this._shape = new Kinetic.Group({
                x: this._x,
                y: this._y,
                width: width,
                height: this._height
            });

            this._bounder = new Kinetic.Rect({
                x: 0,
                y: 0,
                width: width,
                height: this._height,
                stroke: 'black',
                strokeWidth: 0.5
            });

            this._line = new Kinetic.Line({
                points: [this._titleHeight, 0, this._titleHeight, this._height],
                stroke: "black",
                strokeWidth: 0.5
            });
            this._header = new Kinetic.Rect({
                x: 0,
                y: 0,
                width: this._titleHeight,
                height: this._height,
                stroke: 'black',
                strokeWidth: 0.5
            });
            //var name = $.Enumerable.From(BPM.Positions).Single(function (x) {
            //    return x.Id == self._data.group;
            //}).Name;
            var name = $.Enumerable.From(BPM.Positions).Where(function (x) {
                return $.Enumerable.From(self._data.group).Contains(x.Id);
            }).Select(function (x) {
                return x.Name;
            }).ToArray().join(", ");
            this._text = new Kinetic.Text({
                x: 5,
                y: this._height,
                width: this._height,
                text: name,
                fontSize: 14,
                fontFamily: 'Calibri',
                fill: 'green',
                rotationDeg: 270,
                align: 'center'
            });
            this._shape.add(this._bounder);
            this._shape.add(this._line);
            this._shape.add(this._header);
            this._shape.add(this._text);
            this._diagram._layer0.add(this._shape);
        },
        move: function (pos) {
            this._shape.setX(pos.x);
            this._shape.setY(pos.y);
        },
        resize: function (size) {
            var width = size.width;
            var height = size.height;
            this._height = height;
            this._shape.setWidth(width);
            this._shape.setHeight(height);
            this._bounder.setWidth(width);
            this._bounder.setHeight(height);
            this._text.setX(5);
            this._text.setY(height);
            this._text.setWidth(height);
            this._header.setHeight(height);
            this._line.setPoints([this._titleHeight, 0, this._titleHeight, this._height]);
        },
        getNextPos: function () {
            var pos = this._shape.getAbsolutePosition();
            return { x: pos.x, y: pos.y + this._shape.height() };
        }
    }
    BPM.Layer.registerClass("BPM.Layer", BPM.Base);
    //-----------------------------------------------------
    BPM.Stage = function (config) {
        BPM.Stage.initializeBase(this);
        var self = this;
        this._id = config.id;
        this._x = config.x;
        this._y = config.y;
        this._width = config.width;
        this._height = config.height;
        this._name = config.name;
        this._diagram = config.diagram;
        this._titleHeight = 40;
        this._padding = 5;
        this._data = {
            id: config.id,
            code: '',
            name: ko.observable(config.name),
            connections: ko.observableArray([]),
            allowSendBack: config.allowSendBack,
            endWorkflow: config.endWorkflow,
            isLeader: ko.observable(config.isLeader),
            lookupCR: config.lookupCR,
            duration: config.duration,
            remove: function (item) {
                //self._connector.removeConnection(Array.indexOf(self._data.connections(), item));
                //self._phase.diagram().shapeLayer().draw();
                var idx = Array.indexOf(self._data.connections(), item);
                self._diagram._connector.removeConnection(self, idx);
                self._data.connections.remove(item);
            },
            addPoint: function (item) {
                self._diagram._connector.addPoint({ stage: self, idx: Array.indexOf(self._data.connections(), item) });
            },
            tasks: ko.observableArray([]),
            addTask: function () {
                self._data.tasks.push({ id: uuid.v4(), name: "", selectedForms: ko.observableArray([]), selectedFiles: ko.observableArray([]) });
            },
            removeTask: function (item) {
                self._data.tasks.remove(item);
            },
        }
        $(config.tasks).each(function (idx, t) {
            self._data.tasks.push({ id: t.id, name: t.name, selectedForms: ko.observableArray(t.forms), selectedFiles: ko.observableArray(t.files) });
        });
        this.render();
        this._data.name.subscribe(function (value) {
            self._text.setText(value);
            self.updateTextPosition();
            self._diagram._layer1.draw();
            self._diagram._connector.refreshName(self);
        });
        this._data.isLeader.subscribe(function (value) {
            if (value) {
                self._isLeader.show();
                self._isLeaderBounder.show();
            } else {
                self._isLeader.hide();
                self._isLeaderBounder.hide();
            }
            self._diagram._layer1.draw();
        });
    }
    BPM.Stage.prototype = {
        template: function () {
            return "template-stage";
        },
        setActivate: function() {
            this._bounder.setStroke("red");
            this._diagram._layer1.draw();
        },
        render: function () {
            var self = this;
            this._shape = new Kinetic.Group({
                x: this._x,
                y: this._y,
                width: this._width,
                height: this._height,                
            });
            this._bounder = new Kinetic.Rect({
                x: 0,
                y: 0,
                width: this._width,
                height: this._height,
                stroke: 'black',
                strokeWidth: 1
            });
            this._text = new Kinetic.Text({
                x: this._padding,
                y: this._padding,
                width: this._width - this._padding * 2,
                text: this._name,
                fontSize: 14,
                fontFamily: 'Calibri',
                fill: 'green',
                align: 'center'
            });

            this._isLeaderBounder = new Kinetic.Rect({
                x: 0,
                y: 0,
                width: 50,
                height: 16,
                stroke: 'black',
                fill: 'silver',
                strokeWidth: 1
            });
            this._isLeader = new Kinetic.Text({
                x: 0,
                y: 3,
                width: 80,
                text: 'Lãnh đạo',
                fontSize: 11,
                fontFamily: 'Calibri',
                fill: 'green',
                align: 'left'
            });
            this._shape.add(this._bounder);
            this._shape.add(this._text);
            this._shape.add(this._isLeaderBounder);
            this._shape.add(this._isLeader);
            this._diagram._layer1.add(this._shape);
            this.updateTextPosition();
            if (!this._data.isLeader()) {
                this._isLeader.hide();
                this._isLeaderBounder.hide();
            }

        },
        move: function (pos) {
            this._shape.setX(pos.x);
            this._shape.setY(pos.y);
        },
        resize: function (size) {
            var width = size.width;
            var height = size.height;
            this._width = width;
            this._height = height;
            this._shape.setWidth(width);
            this._shape.setHeight(height);
            this._bounder.setWidth(width);
            this._bounder.setHeight(height);
            this._text.setWidth(width - this._padding * 2);
            this.updateTextPosition();
        },
        updateTextPosition: function () {
            var y = this._bounder.y() + this._bounder.height() / 2 - this._text.height() / 2 - this._padding;
            this._text.y(y);
        },
    }
    BPM.Stage.registerClass("BPM.Stage", BPM.Base);
    //--------------------------------------------------
    //---------------------------------------------
    BPM.Connection = function (config) {
        this._diagram = config.diagram;
        this._begin = config.begin;
        this._end = config.end;
        this._points = config.points;
        this._name = config.name;
        this._headlen = 10;
        //this._pointPadding = 5;
        this.render();
    }
    BPM.Connection.prototype = {
        render: function () {
            var self = this;
            this._line = new Kinetic.Line({
                points: this.calculateLinePoints(),
                stroke: "black",
                strokeWidth: 1
            });
            this._diagram._layer2.add(this._line);
        },
        refresh: function () {
            this._line.setPoints(this.calculateLinePoints());
        },
        addPoint: function () {
            var points = this.calculateLinePoints();
            Array.insert(this._points, 0, { x: Math.min(points[0], points[2]) + Math.abs(points[0] - points[2]) / 2, y: Math.min(points[1], points[3]) + Math.abs(points[1] - points[3]) / 2 });            
        },
        calculate2BPoints: function () {
            var points = {};
            var pos1 = this._begin._shape.getAbsolutePosition();
            var pos2 = this._end._shape.getAbsolutePosition();
            var bb1 = { x: pos1.x, y: pos1.y, width: this._begin._shape.width(), height: this._begin._shape.height() },
                bb2 = { x: pos2.x, y: pos2.y, width: this._end._shape.width(), height: this._end._shape.height() };
            if (this._points.length == 0)
                points = this.calculatePoints(bb1, bb2);
            else {
                var firstp = this._points[0];
                var bfirstp = { x: firstp.x, y: firstp.y, width: 1, height: 1 };
                var lastp = this._points[this._points.length - 1];
                var blastp = { x: lastp.x, y: lastp.y, width: 1, height: 1 };
                var ps1 = this.calculatePoints(bb1, bfirstp).pos1;
                var ps2 = this.calculatePoints(blastp, bb2).pos2;
                points = { pos1: ps1, pos2: ps2 };
            }
            return points;
        },
        calculateLinePoints: function () {
            var points = this.calculate2BPoints();
            var point1, point2 = points.pos2;
            if (this._points.length > 0) {
                point1 = this._points[this._points.length - 1];
            } else {
                point1 = points.pos1;
            }
            var fromx = point1.x, fromy = point1.y, tox = point2.x, toy = point2.y;
            var headlen = this._headlen;
            var angle = Math.atan2(toy - fromy, tox - fromx);
            var result = [points.pos1.x, points.pos1.y];
            $(this._points).each(function (idx, p) {
                result.push(p.x);
                result.push(p.y);
            });
            result.push(tox);
            result.push(toy);
            return result.concat([tox - headlen * Math.cos(angle - Math.PI / 6), toy - headlen * Math.sin(angle - Math.PI / 6), tox, toy, tox - headlen * Math.cos(angle + Math.PI / 6), toy - headlen * Math.sin(angle + Math.PI / 6)]);
        },
        calculatePoints: function (bb1, bb2) {
            var p = [{ x: bb1.x + bb1.width / 2, y: bb1.y - 1 },
                    { x: bb1.x + bb1.width / 2, y: bb1.y + bb1.height + 1 },
                    { x: bb1.x - 1, y: bb1.y + bb1.height / 2 },
                    { x: bb1.x + bb1.width + 1, y: bb1.y + bb1.height / 2 },
                    { x: bb2.x + bb2.width / 2, y: bb2.y - 1 },
                    { x: bb2.x + bb2.width / 2, y: bb2.y + bb2.height + 1 },
                    { x: bb2.x - 1, y: bb2.y + bb2.height / 2 },
                    { x: bb2.x + bb2.width + 1, y: bb2.y + bb2.height / 2 }],
                d = {}, dis = [];
            for (var i = 0; i < 4; i++) {
                for (var j = 4; j < 8; j++) {
                    var dx = Math.abs(p[i].x - p[j].x),
                        dy = Math.abs(p[i].y - p[j].y);
                    if ((i == j - 4) || (((i != 3 && j != 6) || p[i].x < p[j].x) && ((i != 2 && j != 7) || p[i].x > p[j].x) && ((i != 0 && j != 5) || p[i].y > p[j].y) && ((i != 1 && j != 4) || p[i].y < p[j].y))) {
                        dis.push(dx + dy);
                        d[dis[dis.length - 1]] = [i, j];
                    }
                }
            }
            if (dis.length == 0) {
                var res = [0, 4];
            } else {
                res = d[Math.min.apply(Math, dis)];
            }
            var x1 = p[res[0]].x,
                y1 = p[res[0]].y,
                x4 = p[res[1]].x,
                y4 = p[res[1]].y;
            return { pos1: { x: x1.toFixed(3), y: y1.toFixed(3) }, pos2: { x: x4.toFixed(3), y: y4.toFixed(3) } };
        },
    }
    BPM.Connection.registerClass("BPM.Connection", BPM.Base);
    //---------------------------------------------
    BPM.Connector = function (config) {
        this._diagram = config.diagram;
        this._connections = {};
    }
    BPM.Connector.prototype = {
        refreshName: function (item) {
            for (var key in this._connections) {
                if (key == item._id)
                    continue;
                var stage = $.Enumerable.From(this._connections[key]).Single(function (x) {
                    return x._end == item;
                });
                var is = $.Enumerable.From(this._connections[key]).IndexOf(stage);
                if (is >= 0)
                    this._connections[key][is]._begin._data.connections()[is].stage = item._data.name();
            }
        },
        addConnection: function (config) {
            //config.begin, config.end
            if (typeof this._connections[config.begin._id] == 'undefined') {
                this._connections[config.begin._id] = [];
            }
            if (typeof config.points == "undefined")
                config.points = [];

            var connections = this._connections[config.begin._id];
            if ($.Enumerable.From(connections).Any(function (x) {
                return x._end._id == config.end._id;
            }))
                return;
            connections.push(new BPM.Connection($.extend(config, { diagram: this._diagram })));
            config.begin._data.connections.push({ name: ko.observable(config.name), rule: config.rule, stage: config.end._data.name() });
        },
        addPoint: function (config) {
            //config.begin, config.end
            this._connections[config.stage._id][config.idx].addPoint();
        },
        setConnectionName: function (config) {
            //config.begin, config.end, config.name
        },
        getConnections: function (stage) {
            return this._connections[stage._id];
        },
        refresh: function (stage) {
            var connections = this._connections[stage._id];
            $(connections).each(function (idx, connection) {
                connection.refresh();
            });
            for (var key in this._connections) {
                if (key == stage._id)
                    continue;
                $(this._connections[key]).each(function (idx, conn) {
                    if (conn._end._id == stage._id)
                        conn.refresh();
                });
            }
            this._diagram._layer2.draw();
        }
    }
    BPM.Connector.registerClass("BPM.Connector", BPM.Base);
    //--------------------------------------------------------------------------
    var vm = function () {
        var self = this;
        this.model = {
            width: ko.observable(),
            height: ko.observable()
        }
        
        this.cancel = function () {
            router.navigate("#product");
        }
        this.addPhase = function () {
            self.diagram.addPhase();
        }
        this.addLayer = function () {
            self.diagram.addLayer();
        }
        this.addStage = function () {
            self.diagram.addStage();
        }
        this.remove = function () {
            self.diagram.removeItem();
        }
        this.cancel = function () {

        }

        this.activate = function (id) {
            self.pid = id;
            self.appLink = String.format("#application/{0}", id);
            self.appId = id;
            $.ajax({
                url: String.format("/Application/GetWorkflowData/{0}", self.appId),
                async: false,
                success: function (data) {
                    self.workflowData = data.DesignData;
                    self.stageId = data.CurrentStage;
                    BPM.Positions = data.Positions;
                }
            });
        }
        this.attached = function (view) {
            
            var diagram = new BPM.Diagram({ element: $("#pnDiagram")[0], width: 3000, height: 4000 });
            self.diagram = diagram;
            if (self.workflowData) {
                self.diagram.load(Sys.Serialization.JavaScriptSerializer.deserialize(self.workflowData));
                var stg = $.Enumerable.From(self.diagram._stages).Single(function(x) {
                    return x._id == self.stageId;
                });
                stg.setActivate();
            }
        }
    }
    return vm;
});