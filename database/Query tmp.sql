select user_Name,
		user_Password,
		user_FullName,
		user_Email,
		dep_Name,
		dep_Code,
		role_Name 
from Users u left join Department d 
	on d.dep_Id = u.dep_Id left join Role r
	on r.role_Id = u.role_Id
