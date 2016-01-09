define(['knockout', 'visjs', './history_form', 'linq', 'ma'], function (ko, vis, history_form) {
    var vm = function () {
        var self = this;
        self.selectedNodes = ko.observableArray();
        self.getRandomColor = function () {
            var letters = '0123456789ABCDEF'.split('');
            var color = '#';
            for (var i = 0; i < 6; i++) {
                color += letters[Math.floor(Math.random() * 16)];
            }
            return color;
        }
        this.showForm = function (data) {
            history_form.show({ appId: self.appId, formId: data.Id });
        }
        this.activate = function (id) {
            self.appId = id;            
        }
        this.drawLogs = function (logs) {
            var nodes = $.Enumerable.From(logs).Select(function (x) {
                return { id: x.StageId, label: x.StageName, shape: 'box' };
            }).Distinct(function (x) {
                return x.id;
            }).ToArray();
            nodes[0].color = 'red';
            nodes[nodes.length - 1].color = 'silver';
            var edges = [];
            var color = self.getRandomColor();
            for (var i = 0; i < logs.length - 1; i++) {
                if (logs[i + 1].IsRollback)
                    color = self.getRandomColor();
                edges.push({ from: logs[i].StageId, to: logs[i + 1].StageId, style: 'arrow', label: i + 1, color: color });
                logs[i].nodeNumber = i + 1;
            }
            logs[logs.length - 1].nodeNumber = logs.length;

            var container = document.getElementById('history_graph');
            var data = {
                nodes: nodes,
                edges: edges
            };
            var options = {
                physics: {
                    barnesHut: {
                        gravitationalConstant: -4000,
                        springLength: 300
                    }
                }
            };
            var network = new vis.Network(container, data, options);
            return network;
        }
        this.attached = function () {
            $.ajax({
                url: String.format("/Application/GetHistory/?appId={0}&desc=false", self.appId),
                async: false,
                success: function (logs) {
                    self.logs = logs;
                }
            });
            var network = self.drawLogs(self.logs);
            network.on('select', function (properties) {
                var stageId = properties.nodes;
                var logs = $.Enumerable.From(self.logs).Where(function (x) {
                    return x.StageId == stageId;
                }).ToArray();
                self.selectedNodes(logs);
            });
        }
    }
    return vm;
})