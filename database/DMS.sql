USE [DMS]
GO
/****** Object:  Table [dbo].[Action]    Script Date: 6/1/2016 9:57:32 CH ******/
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
/****** Object:  Table [dbo].[Application]    Script Date: 6/1/2016 9:57:32 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Application](
	[app_Id] [uniqueidentifier] NOT NULL,
	[app_Name] [nvarchar](100) NULL,
	[app_IsActive] [bit] NULL,
	[app_CreateDate] [datetime] NULL,
	[app_IsWorkFlow] [bit] NULL,
	[app_Type] [tinyint] NULL,
	[app_isVisible] [bit] NULL,
 CONSTRAINT [PK_Application] PRIMARY KEY CLUSTERED 
(
	[app_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Department]    Script Date: 6/1/2016 9:57:32 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Department](
	[dep_Id] [uniqueidentifier] NOT NULL,
	[dep_Name] [nvarchar](100) NOT NULL,
	[dep_Status] [tinyint] NOT NULL,
	[dep_Code] [varchar](10) NULL,
 CONSTRAINT [PK_Department] PRIMARY KEY CLUSTERED 
(
	[dep_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Document]    Script Date: 6/1/2016 9:57:32 CH ******/
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
	[doc_UpdateDate] [datetime] NULL,
	[doc_CreateDate] [datetime] NULL,
	[user_Id] [uniqueidentifier] NULL,
	[docType_Id] [varchar](30) NULL,
	[doc_Content] [nvarchar](500) NULL,
	[doc_Status] [tinyint] NULL,
	[app_Id] [uniqueidentifier] NULL,
	[doc_IsValid] [nchar](10) NULL,
	[doc_ValidFrom] [varchar](10) NULL,
	[doc_ValidTo] [varchar](10) NULL,
	[doc_Date] [varchar](10) NULL,
	[doc_Type] [tinyint] NULL,
 CONSTRAINT [PK_Document] PRIMARY KEY CLUSTERED 
(
	[doc_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[DocumentDepartment]    Script Date: 6/1/2016 9:57:32 CH ******/
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
/****** Object:  Table [dbo].[DocumentDetail]    Script Date: 6/1/2016 9:57:32 CH ******/
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
 CONSTRAINT [PK_DocumentDetail] PRIMARY KEY CLUSTERED 
(
	[docDetail_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[DocumentStaff]    Script Date: 6/1/2016 9:57:32 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DocumentStaff](
	[docDep_Id] [uniqueidentifier] NULL,
	[user_Id] [uniqueidentifier] NULL,
	[docStaff_Id] [uniqueidentifier] NOT NULL,
	[docStaff_IsActive] [bit] NULL,
 CONSTRAINT [PK_DocumentStaff] PRIMARY KEY CLUSTERED 
(
	[docStaff_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[DocumentStorage]    Script Date: 6/1/2016 9:57:32 CH ******/
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
/****** Object:  Table [dbo].[DocumentType]    Script Date: 6/1/2016 9:57:32 CH ******/
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
/****** Object:  Table [dbo].[GroupDepartment]    Script Date: 6/1/2016 9:57:32 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GroupDepartment](
	[groupDep_Id] [uniqueidentifier] NOT NULL,
	[groupDep_Name] [nvarchar](100) NOT NULL,
	[groupDep_Status] [smallint] NOT NULL,
 CONSTRAINT [PK_GroupDepartment] PRIMARY KEY CLUSTERED 
(
	[groupDep_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[GroupDepartmentDetail]    Script Date: 6/1/2016 9:57:32 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GroupDepartmentDetail](
	[groupDep_Id] [uniqueidentifier] NOT NULL,
	[dep_Id] [uniqueidentifier] NOT NULL,
	[groupDepDetail_Id] [uniqueidentifier] NOT NULL,
 CONSTRAINT [PK_GroupDepartmentDetail_1] PRIMARY KEY CLUSTERED 
(
	[groupDepDetail_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Role]    Script Date: 6/1/2016 9:57:32 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Role](
	[role_Id] [uniqueidentifier] NOT NULL,
	[role_Name] [varchar](20) NULL,
	[role_Description] [nvarchar](250) NULL,
	[role_CreateDate] [datetime] NULL,
 CONSTRAINT [PK_Role] PRIMARY KEY CLUSTERED 
(
	[role_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[User]    Script Date: 6/1/2016 9:57:32 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[User](
	[user_Id] [uniqueidentifier] NOT NULL,
	[user_Name] [varchar](20) NOT NULL,
	[user_Password] [varchar](200) NOT NULL,
	[user_CreateDate] [datetime] NULL,
	[user_UpdateDate] [datetime] NULL,
	[user_Gender] [tinyint] NOT NULL,
	[user_DOB] [varchar](10) NULL,
	[user_FullName] [nvarchar](100) NOT NULL,
	[user_Status] [tinyint] NOT NULL,
	[user_Email] [varchar](100) NOT NULL,
	[dep_Id] [uniqueidentifier] NULL,
	[role_Id] [uniqueidentifier] NULL,
 CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED 
(
	[user_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[WorkFlow]    Script Date: 6/1/2016 9:57:32 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[WorkFlow](
	[workFlow_Id] [uniqueidentifier] NOT NULL,
	[workFlow_Name] [nvarchar](200) NULL,
	[workFlow_Step] [tinyint] NULL,
	[role_Id] [uniqueidentifier] NULL,
	[workFlow_Type] [tinyint] NULL,
	[app_Id] [uniqueidentifier] NOT NULL,
	[workFlow_IsAllowReturn] [bit] NOT NULL,
	[workFlow_IsAllowRemove] [bit] NOT NULL,
	[workFlow_IsAllowFinish] [bit] NOT NULL,
	[workFlow_IsAllowUpload] [bit] NOT NULL,
	[workFlow_IsSetViewed] [bit] NOT NULL,
	[workFlow_IsSubmit] [bit] NOT NULL,
	[workFlow_IsTransferMultiple] [bit] NOT NULL,
	[workFlow_IsGenerateNumber] [bit] NOT NULL,
 CONSTRAINT [PK_WorkFlow] PRIMARY KEY CLUSTERED 
(
	[workFlow_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
ALTER TABLE [dbo].[Application] ADD  CONSTRAINT [DF_Application_app_CreateDate]  DEFAULT (getdate()) FOR [app_CreateDate]
GO
ALTER TABLE [dbo].[Department] ADD  CONSTRAINT [DF_Table_1_dep_Code]  DEFAULT (newid()) FOR [dep_Id]
GO
ALTER TABLE [dbo].[Department] ADD  CONSTRAINT [DF_Department_dep_Status]  DEFAULT ((1)) FOR [dep_Status]
GO
ALTER TABLE [dbo].[Document] ADD  CONSTRAINT [DF_Document_doc_Id]  DEFAULT (newid()) FOR [doc_Id]
GO
ALTER TABLE [dbo].[Document] ADD  CONSTRAINT [DF_Document_doc_CreateDate]  DEFAULT (getdate()) FOR [doc_CreateDate]
GO
ALTER TABLE [dbo].[DocumentDepartment] ADD  CONSTRAINT [DF_DocumentDepartment_docDep_IsActive]  DEFAULT ((1)) FOR [docDep_IsActive]
GO
ALTER TABLE [dbo].[DocumentDetail] ADD  CONSTRAINT [DF_DocumentDetail_docDetail_CreateDate]  DEFAULT (getdate()) FOR [docDetail_CreateDate]
GO
ALTER TABLE [dbo].[DocumentStaff] ADD  CONSTRAINT [DF_DocumentStaff_docStaff_Id]  DEFAULT (newid()) FOR [docStaff_Id]
GO
ALTER TABLE [dbo].[DocumentStorage] ADD  CONSTRAINT [DF_DocumentStorage_docSto_CreateDate]  DEFAULT (getdate()) FOR [docSto_CreateDate]
GO
ALTER TABLE [dbo].[GroupDepartment] ADD  CONSTRAINT [DF_GroupDepartment_groupDep_Id]  DEFAULT (newid()) FOR [groupDep_Id]
GO
ALTER TABLE [dbo].[Role] ADD  CONSTRAINT [DF_Role_role_Code]  DEFAULT (newid()) FOR [role_Id]
GO
ALTER TABLE [dbo].[Role] ADD  CONSTRAINT [DF_Role_role_CreateDate]  DEFAULT (getdate()) FOR [role_CreateDate]
GO
ALTER TABLE [dbo].[User] ADD  CONSTRAINT [DF_User_user_Code]  DEFAULT (newid()) FOR [user_Id]
GO
ALTER TABLE [dbo].[User] ADD  CONSTRAINT [DF_User_user_Status]  DEFAULT ((1)) FOR [user_Status]
GO
ALTER TABLE [dbo].[WorkFlow] ADD  CONSTRAINT [DF_WorkFlow_workFlow_Id]  DEFAULT (newid()) FOR [workFlow_Id]
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
REFERENCES [dbo].[User] ([user_Id])
GO
ALTER TABLE [dbo].[Document] CHECK CONSTRAINT [FK_Document_User]
GO
ALTER TABLE [dbo].[DocumentDepartment]  WITH CHECK ADD  CONSTRAINT [FK_DocumentDepartment_Department] FOREIGN KEY([dep_Id])
REFERENCES [dbo].[Department] ([dep_Id])
GO
ALTER TABLE [dbo].[DocumentDepartment] CHECK CONSTRAINT [FK_DocumentDepartment_Department]
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
REFERENCES [dbo].[User] ([user_Id])
GO
ALTER TABLE [dbo].[DocumentStaff] CHECK CONSTRAINT [FK_DocumentStaff_User]
GO
ALTER TABLE [dbo].[DocumentStorage]  WITH CHECK ADD  CONSTRAINT [FK_DocumentStorage_Document] FOREIGN KEY([doc_Id])
REFERENCES [dbo].[Document] ([doc_Id])
GO
ALTER TABLE [dbo].[DocumentStorage] CHECK CONSTRAINT [FK_DocumentStorage_Document]
GO
ALTER TABLE [dbo].[DocumentStorage]  WITH CHECK ADD  CONSTRAINT [FK_DocumentStorage_User] FOREIGN KEY([user_Id])
REFERENCES [dbo].[User] ([user_Id])
GO
ALTER TABLE [dbo].[DocumentStorage] CHECK CONSTRAINT [FK_DocumentStorage_User]
GO
ALTER TABLE [dbo].[GroupDepartmentDetail]  WITH CHECK ADD  CONSTRAINT [FK_GroupDepartmentDetail_Department] FOREIGN KEY([dep_Id])
REFERENCES [dbo].[Department] ([dep_Id])
GO
ALTER TABLE [dbo].[GroupDepartmentDetail] CHECK CONSTRAINT [FK_GroupDepartmentDetail_Department]
GO
ALTER TABLE [dbo].[GroupDepartmentDetail]  WITH CHECK ADD  CONSTRAINT [FK_GroupDepartmentDetail_GroupDepartment] FOREIGN KEY([groupDep_Id])
REFERENCES [dbo].[GroupDepartment] ([groupDep_Id])
GO
ALTER TABLE [dbo].[GroupDepartmentDetail] CHECK CONSTRAINT [FK_GroupDepartmentDetail_GroupDepartment]
GO
ALTER TABLE [dbo].[User]  WITH CHECK ADD  CONSTRAINT [FK_User_Department] FOREIGN KEY([dep_Id])
REFERENCES [dbo].[Department] ([dep_Id])
GO
ALTER TABLE [dbo].[User] CHECK CONSTRAINT [FK_User_Department]
GO
ALTER TABLE [dbo].[User]  WITH CHECK ADD  CONSTRAINT [FK_User_Role] FOREIGN KEY([role_Id])
REFERENCES [dbo].[Role] ([role_Id])
GO
ALTER TABLE [dbo].[User] CHECK CONSTRAINT [FK_User_Role]
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
