USE [DMS]
GO
/****** Object:  Table [dbo].[Action]    Script Date: 11/1/2016 12:19:38 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Action](
	[act_Id] [tinyint] NOT NULL,
	[act_Name] [nvarchar](100) NULL,
	[act_Description] [nvarchar](150) NULL,
 CONSTRAINT [PK_Action] PRIMARY KEY CLUSTERED 
(
	[act_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Application]    Script Date: 11/1/2016 12:19:38 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Application](
	[app_Id] [uniqueidentifier] NOT NULL CONSTRAINT [DF_Application_app_Id]  DEFAULT (newid()),
	[app_Name] [nvarchar](100) NULL,
	[app_IsActive] [bit] NULL CONSTRAINT [DF_Application_app_IsActive]  DEFAULT ((1)),
	[app_CreateDate] [datetime] NULL CONSTRAINT [DF_Application_app_CreateDate]  DEFAULT (getdate()),
	[app_IsWorkFlow] [bit] NULL,
	[app_Type] [tinyint] NULL,
	[app_isReply] [bit] NULL CONSTRAINT [DF_Application_app_isVisible]  DEFAULT ((1)),
 CONSTRAINT [PK_Application] PRIMARY KEY CLUSTERED 
(
	[app_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Department]    Script Date: 11/1/2016 12:19:38 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Department](
	[dep_Id] [uniqueidentifier] NOT NULL CONSTRAINT [DF_Table_1_dep_Code]  DEFAULT (newid()),
	[dep_Name] [nvarchar](100) NOT NULL,
	[dep_Status] [tinyint] NOT NULL CONSTRAINT [DF_Department_dep_Status]  DEFAULT ((1)),
	[dep_Code] [varchar](10) NULL,
 CONSTRAINT [PK_Department] PRIMARY KEY CLUSTERED 
(
	[dep_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Document]    Script Date: 11/1/2016 12:19:38 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Document](
	[doc_Id] [uniqueidentifier] NOT NULL,
	[doc_Number] [varchar](50) NULL,
	[doc_SourceNumber] [varchar](50) NULL,
	[doc_UpdateDate] [datetime] NOT NULL,
	[doc_CreateDate] [datetime] NOT NULL,
	[user_Id] [uniqueidentifier] NOT NULL,
	[docType_Id] [varchar](30) NOT NULL,
	[doc_Content] [nvarchar](500) NULL,
	[doc_Status] [tinyint] NULL,
	[app_Id] [uniqueidentifier] NOT NULL,
	[doc_IsValid] [bit] NOT NULL,
	[doc_ValidFrom] [varchar](10) NULL,
	[doc_ValidTo] [varchar](10) NULL,
	[doc_Date] [varchar](10) NULL,
	[doc_Type] [tinyint] NOT NULL,
	[doc_isNeedReply] [bit] NOT NULL,
	[doc_isReply] [bit] NOT NULL,
 CONSTRAINT [PK_Document] PRIMARY KEY CLUSTERED 
(
	[doc_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[DocumentDepartment]    Script Date: 11/1/2016 12:19:38 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DocumentDepartment](
	[docDetail_Id] [uniqueidentifier] NOT NULL,
	[dep_Id] [uniqueidentifier] NOT NULL,
	[docDep_Id] [uniqueidentifier] NOT NULL,
	[docDep_IsActive] [bit] NULL,
 CONSTRAINT [PK_DocumentDepartment_1] PRIMARY KEY CLUSTERED 
(
	[docDep_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[DocumentDetail]    Script Date: 11/1/2016 12:19:38 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[DocumentDetail](
	[docDetail_Id] [uniqueidentifier] NOT NULL,
	[doc_Id] [uniqueidentifier] NULL,
	[docDetail_UserCreate] [uniqueidentifier] NULL,
	[docDetail_DepCreate] [uniqueidentifier] NULL,
	[docDetail_UserReceive] [uniqueidentifier] NULL,
	[docDetail_DepReceive] [uniqueidentifier] NULL,
	[docDetail_FileName] [varchar](150) NULL,
	[docDetail_FileContent] [image] NULL,
	[workFlow_Id] [uniqueidentifier] NULL,
	[docDetail_CreateDate] [datetime] NULL,
	[docDetail_Description] [nvarchar](500) NULL,
	[act_Id] [tinyint] NULL,
	[docDetail_IsActive] [bit] NULL,
	[docDetail_UpdateDate] [datetime] NULL,
	[docDetail_IsUrgent] [bit] NULL,
	[docDetail_IsEdit] [bit] NULL,
 CONSTRAINT [PK_DocumentDetail] PRIMARY KEY CLUSTERED 
(
	[docDetail_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[DocumentStaff]    Script Date: 11/1/2016 12:19:38 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DocumentStaff](
	[docDep_Id] [uniqueidentifier] NOT NULL,
	[user_Id] [uniqueidentifier] NOT NULL,
	[docStaff_Id] [uniqueidentifier] NOT NULL,
	[docStaff_IsActive] [bit] NULL,
 CONSTRAINT [PK_DocumentStaff] PRIMARY KEY CLUSTERED 
(
	[docStaff_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[DocumentStorage]    Script Date: 11/1/2016 12:19:38 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DocumentStorage](
	[doc_Id] [uniqueidentifier] NOT NULL,
	[user_Id] [uniqueidentifier] NOT NULL,
	[docSto_CreateDate] [datetime] NOT NULL,
	[docSto_Id] [uniqueidentifier] NOT NULL,
 CONSTRAINT [PK_DocumentStorage_1] PRIMARY KEY CLUSTERED 
(
	[docSto_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[DocumentType]    Script Date: 11/1/2016 12:19:38 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[DocumentType](
	[docType_Id] [varchar](30) NOT NULL,
	[docType_Name] [nvarchar](50) NULL,
 CONSTRAINT [PK_DocumentType] PRIMARY KEY CLUSTERED 
(
	[docType_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Role]    Script Date: 11/1/2016 12:19:38 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Role](
	[role_Id] [uniqueidentifier] NOT NULL CONSTRAINT [DF_Role_role_Code]  DEFAULT (newid()),
	[role_Name] [varchar](20) NULL,
	[role_Description] [nvarchar](250) NULL,
	[role_CreateDate] [datetime] NULL CONSTRAINT [DF_Role_role_CreateDate]  DEFAULT (getdate()),
 CONSTRAINT [PK_Role] PRIMARY KEY CLUSTERED 
(
	[role_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Users]    Script Date: 11/1/2016 12:19:38 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Users](
	[user_Id] [uniqueidentifier] NOT NULL CONSTRAINT [DF_User_user_Code]  DEFAULT (newid()),
	[user_Name] [varchar](20) NOT NULL,
	[user_Password] [varchar](200) NOT NULL,
	[user_CreateDate] [datetime] NULL CONSTRAINT [DF_User_user_CreateDate]  DEFAULT (getdate()),
	[user_UpdateDate] [datetime] NULL CONSTRAINT [DF_User_user_UpdateDate]  DEFAULT (getdate()),
	[user_Gender] [tinyint] NOT NULL,
	[user_DOB] [varchar](10) NULL,
	[user_FullName] [nvarchar](100) NOT NULL,
	[user_Status] [tinyint] NOT NULL CONSTRAINT [DF_User_user_Status]  DEFAULT ((1)),
	[user_Email] [varchar](100) NOT NULL,
	[dep_Id] [uniqueidentifier] NOT NULL,
	[role_Id] [uniqueidentifier] NOT NULL,
 CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED 
(
	[user_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[WorkFlow]    Script Date: 11/1/2016 12:19:38 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[WorkFlow](
	[workFlow_Id] [uniqueidentifier] NOT NULL CONSTRAINT [DF_WorkFlow_workFlow_Id]  DEFAULT (newid()),
	[workFlow_Name] [nvarchar](200) NULL,
	[workFlow_Step] [tinyint] NULL,
	[role_Id] [uniqueidentifier] NULL,
	[app_Id] [uniqueidentifier] NOT NULL,
	[workFlow_IsAllowReturn] [bit] NOT NULL,
	[workFlow_IsAllowRemove] [bit] NOT NULL,
	[workFlow_IsAllowFinish] [bit] NOT NULL,
	[workFlow_IsAllowUpload] [bit] NOT NULL,
	[workFlow_IsSetViewed] [bit] NOT NULL,
	[workFlow_IsSubmit] [bit] NOT NULL,
	[workFlow_IsTransferMultiple] [bit] NOT NULL,
	[workFlow_IsGenerateNumber] [bit] NOT NULL,
	[workFlow_CreateDate] [datetime] NULL CONSTRAINT [DF_WorkFlow_workFlow_CreateDate]  DEFAULT (getdate()),
	[workFlow_ChooseType] [tinyint] NULL,
 CONSTRAINT [PK_WorkFlow] PRIMARY KEY CLUSTERED 
(
	[workFlow_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
ALTER TABLE [dbo].[Document] ADD  CONSTRAINT [DF_Document_doc_Id]  DEFAULT (newid()) FOR [doc_Id]
GO
ALTER TABLE [dbo].[Document] ADD  CONSTRAINT [DF_Document_doc_UpdateDate]  DEFAULT (getdate()) FOR [doc_UpdateDate]
GO
ALTER TABLE [dbo].[Document] ADD  CONSTRAINT [DF_Document_doc_CreateDate]  DEFAULT (getdate()) FOR [doc_CreateDate]
GO
ALTER TABLE [dbo].[DocumentDepartment] ADD  CONSTRAINT [DF_DocumentDepartment_docDep_Id]  DEFAULT (newid()) FOR [docDep_Id]
GO
ALTER TABLE [dbo].[DocumentDepartment] ADD  CONSTRAINT [DF_DocumentDepartment_docDep_IsActive]  DEFAULT ((1)) FOR [docDep_IsActive]
GO
ALTER TABLE [dbo].[DocumentDetail] ADD  CONSTRAINT [DF_DocumentDetail_docDetail_CreateDate]  DEFAULT (getdate()) FOR [docDetail_CreateDate]
GO
ALTER TABLE [dbo].[DocumentDetail] ADD  CONSTRAINT [DF_DocumentDetail_docDetail_IsUrgent]  DEFAULT ((0)) FOR [docDetail_IsUrgent]
GO
ALTER TABLE [dbo].[DocumentDetail] ADD  CONSTRAINT [DF_DocumentDetail_docDetail_IsEdit]  DEFAULT ((0)) FOR [docDetail_IsEdit]
GO
ALTER TABLE [dbo].[DocumentStaff] ADD  CONSTRAINT [DF_DocumentStaff_docStaff_Id]  DEFAULT (newid()) FOR [docStaff_Id]
GO
ALTER TABLE [dbo].[DocumentStaff] ADD  CONSTRAINT [DF_DocumentStaff_docStaff_IsActive]  DEFAULT ((1)) FOR [docStaff_IsActive]
GO
ALTER TABLE [dbo].[DocumentStorage] ADD  CONSTRAINT [DF_DocumentStorage_docSto_CreateDate]  DEFAULT (getdate()) FOR [docSto_CreateDate]
GO
ALTER TABLE [dbo].[DocumentStorage] ADD  CONSTRAINT [DF_DocumentStorage_docSto_Id]  DEFAULT (newid()) FOR [docSto_Id]
GO
ALTER TABLE [dbo].[Document]  WITH CHECK ADD  CONSTRAINT [FK_Document_Application] FOREIGN KEY([app_Id])
REFERENCES [dbo].[Application] ([app_Id])
GO
ALTER TABLE [dbo].[Document] CHECK CONSTRAINT [FK_Document_Application]
GO
ALTER TABLE [dbo].[Document]  WITH CHECK ADD  CONSTRAINT [FK_Document_DocumentType] FOREIGN KEY([docType_Id])
REFERENCES [dbo].[DocumentType] ([docType_Id])
GO
ALTER TABLE [dbo].[Document] CHECK CONSTRAINT [FK_Document_DocumentType]
GO
ALTER TABLE [dbo].[Document]  WITH CHECK ADD  CONSTRAINT [FK_Document_User] FOREIGN KEY([user_Id])
REFERENCES [dbo].[Users] ([user_Id])
GO
ALTER TABLE [dbo].[Document] CHECK CONSTRAINT [FK_Document_User]
GO
ALTER TABLE [dbo].[DocumentDepartment]  WITH CHECK ADD  CONSTRAINT [FK_DocumentDepartment_Department] FOREIGN KEY([dep_Id])
REFERENCES [dbo].[Department] ([dep_Id])
GO
ALTER TABLE [dbo].[DocumentDepartment] CHECK CONSTRAINT [FK_DocumentDepartment_Department]
GO
ALTER TABLE [dbo].[DocumentDepartment]  WITH CHECK ADD  CONSTRAINT [FK_DocumentDepartment_DocumentDetail] FOREIGN KEY([docDetail_Id])
REFERENCES [dbo].[DocumentDetail] ([docDetail_Id])
GO
ALTER TABLE [dbo].[DocumentDepartment] CHECK CONSTRAINT [FK_DocumentDepartment_DocumentDetail]
GO
ALTER TABLE [dbo].[DocumentDetail]  WITH CHECK ADD  CONSTRAINT [FK_DocumentDetail_Action] FOREIGN KEY([act_Id])
REFERENCES [dbo].[Action] ([act_Id])
GO
ALTER TABLE [dbo].[DocumentDetail] CHECK CONSTRAINT [FK_DocumentDetail_Action]
GO
ALTER TABLE [dbo].[DocumentDetail]  WITH CHECK ADD  CONSTRAINT [FK_DocumentDetail_Document] FOREIGN KEY([doc_Id])
REFERENCES [dbo].[Document] ([doc_Id])
GO
ALTER TABLE [dbo].[DocumentDetail] CHECK CONSTRAINT [FK_DocumentDetail_Document]
GO
ALTER TABLE [dbo].[DocumentDetail]  WITH CHECK ADD  CONSTRAINT [FK_DocumentDetail_WorkFlow] FOREIGN KEY([workFlow_Id])
REFERENCES [dbo].[WorkFlow] ([workFlow_Id])
GO
ALTER TABLE [dbo].[DocumentDetail] CHECK CONSTRAINT [FK_DocumentDetail_WorkFlow]
GO
ALTER TABLE [dbo].[DocumentStaff]  WITH CHECK ADD  CONSTRAINT [FK_DocumentStaff_DocumentDepartment] FOREIGN KEY([docDep_Id])
REFERENCES [dbo].[DocumentDepartment] ([docDep_Id])
GO
ALTER TABLE [dbo].[DocumentStaff] CHECK CONSTRAINT [FK_DocumentStaff_DocumentDepartment]
GO
ALTER TABLE [dbo].[DocumentStaff]  WITH CHECK ADD  CONSTRAINT [FK_DocumentStaff_User] FOREIGN KEY([user_Id])
REFERENCES [dbo].[Users] ([user_Id])
GO
ALTER TABLE [dbo].[DocumentStaff] CHECK CONSTRAINT [FK_DocumentStaff_User]
GO
ALTER TABLE [dbo].[DocumentStorage]  WITH CHECK ADD  CONSTRAINT [FK_DocumentStorage_Document] FOREIGN KEY([doc_Id])
REFERENCES [dbo].[Document] ([doc_Id])
GO
ALTER TABLE [dbo].[DocumentStorage] CHECK CONSTRAINT [FK_DocumentStorage_Document]
GO
ALTER TABLE [dbo].[DocumentStorage]  WITH CHECK ADD  CONSTRAINT [FK_DocumentStorage_User] FOREIGN KEY([user_Id])
REFERENCES [dbo].[Users] ([user_Id])
GO
ALTER TABLE [dbo].[DocumentStorage] CHECK CONSTRAINT [FK_DocumentStorage_User]
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD  CONSTRAINT [FK_User_Department] FOREIGN KEY([dep_Id])
REFERENCES [dbo].[Department] ([dep_Id])
GO
ALTER TABLE [dbo].[Users] CHECK CONSTRAINT [FK_User_Department]
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD  CONSTRAINT [FK_User_Role] FOREIGN KEY([role_Id])
REFERENCES [dbo].[Role] ([role_Id])
GO
ALTER TABLE [dbo].[Users] CHECK CONSTRAINT [FK_User_Role]
GO
ALTER TABLE [dbo].[WorkFlow]  WITH CHECK ADD  CONSTRAINT [FK_WorkFlow_Application] FOREIGN KEY([app_Id])
REFERENCES [dbo].[Application] ([app_Id])
GO
ALTER TABLE [dbo].[WorkFlow] CHECK CONSTRAINT [FK_WorkFlow_Application]
GO
ALTER TABLE [dbo].[WorkFlow]  WITH CHECK ADD  CONSTRAINT [FK_WorkFlow_Role] FOREIGN KEY([role_Id])
REFERENCES [dbo].[Role] ([role_Id])
GO
ALTER TABLE [dbo].[WorkFlow] CHECK CONSTRAINT [FK_WorkFlow_Role]
GO
