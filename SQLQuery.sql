use master
go

DROP DATABASE IF EXISTS LeaveManagement
CREATE DATABASE LeaveManagement;
GO

USE LeaveManagement;
GO

DROP TABLE IF EXISTS Departments
CREATE TABLE Departments (
	DepartmentID INT IDENTITY(1,1) PRIMARY KEY,
    DepartmentName VARCHAR(100) NOT NULL,
	Status BIT NOT NULL DEFAULT 1
);
GO

DROP TABLE IF EXISTS Positions;
CREATE TABLE Positions (
    PositionID INT IDENTITY(1,1) PRIMARY KEY,
	DepartmentID INT NOT NULL,
    PositionName VARCHAR(100) NOT NULL,
	Status BIT NOT NULL DEFAULT 1,
	FOREIGN KEY (DepartmentID) REFERENCES Departments(DepartmentID)
);
GO

DROP TABLE IF EXISTS Employees
CREATE TABLE Employees (
    EmployeeID INT IDENTITY(1,1) PRIMARY KEY,
    FullName VARCHAR(100) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
	Password NVARCHAR(255) NOT NULL,
	Status BIT NOT NULL DEFAULT 1,
);
GO

DROP TABLE IF EXISTS Employee_positions;
CREATE TABLE Employee_positions (
    ID INT IDENTITY(1,1) PRIMARY KEY,
	EmployeeID INT Not NULL,
	PositionID INT Not NUll,
	FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID),
	FOREIGN KEY (PositionID) REFERENCES Positions(PositionID),
);
GO
DROP TABLE IF EXISTS Roles;
CREATE TABLE Roles (
    RoleID INT IDENTITY(1,1) PRIMARY KEY,
    RoleName VARCHAR(50) NOT NULL,
	Status BIT NOT NULL DEFAULT 1
);
GO
DROP TABLE IF EXISTS EmployeeRoles;
CREATE TABLE EmployeeRoles (
    EmployeeRoleID INT IDENTITY(1,1) PRIMARY KEY,
    EmployeeID INT,
    RoleID INT,Status BIT NOT NULL DEFAULT 1,
    FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID),
    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID)
);
GO

DROP TABLE IF EXISTS EmployeeDetails;
CREATE TABLE EmployeeDetails (
	ID INT IDENTITY(1,1) PRIMARY KEY,
    EmployeeID INT,
    Image NVARCHAR(255),
    PhoneNumber VARCHAR(15),
    Address VARCHAR(255),
    DateOfBirth DATE NOT NULL,
    Gender VARCHAR(10),
	Status BIT NOT NULL DEFAULT 1,
    FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID),
);
GO

DROP TABLE IF EXISTS LeaveTypes
CREATE TABLE LeaveTypes (
    LeaveTypeID INT IDENTITY(1,1) PRIMARY KEY,
    LeaveTypeName VARCHAR(100) NOT NULL,
    LeaveTypeDescription TEXT,
	LeaveDaysPerYear INT NOT NULL, -- Number of leave days allowed per year
	Status BIT NOT NULL DEFAULT 1
);

DROP TABLE IF EXISTS LeaveRequests
CREATE TABLE LeaveRequests (
    LeaveRequestID INT IDENTITY(1,1) PRIMARY KEY,
    EmployeeID INT NOT NULL,
	LeaveTypeID INT NOT NULL, 
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    Reason NVARCHAR(255),
    StatusLR VARCHAR(20) DEFAULT 'Submitted' CHECK (StatusLR IN ('Submitted', 'Viewed by Lead', 'Approved by Lead', 'Viewed by HR', 'Approved by HR', 'Rejected')), -- 'Draft', 'Submitted', 'Viewed by Team Lead', 'Approved by Team Lead', 'Viewed by PM', 'Approved by PM', 'Not Approved', 'Canceled'
    SubmissionDate DATETIME NOT NULL DEFAULT GETDATE(),
	ApproverID INT,
    ApprovalDate DATETIME,
	Status BIT NOT NULL DEFAULT 1,
    FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID),
	FOREIGN KEY (LeaveTypeID) REFERENCES LeaveTypes(LeaveTypeID),
	FOREIGN KEY (ApproverID) REFERENCES Employees(EmployeeID)
);
go

-- send 
drop table if exists Notifications
CREATE TABLE Notifications (
    NotificationID INT IDENTITY(1,1) PRIMARY KEY,
    LeaveRequestID INT NOT NULL,
    NotificationDate DATE NOT NULL, --Ngày thông báo được gửi
    ReceiverID INT NOT NULL, -- ID of the Team Lead or PM who receives the notification, xác định người nhận đc thông báo 
    Message NVARCHAR(255), --Nội dung thông báo,  chi tiết việc thay đổi trạng thái hoặc hành động. NV ycau nghỉ --> 1 thông báo gửi đến lead (app/reje) -> tb cho pm(app/re) --> thông báo cuối cùng sẽ đc gửi cho NV
	Status BIT NOT NULL DEFAULT 1,
    FOREIGN KEY (LeaveRequestID) REFERENCES LeaveRequests(LeaveRequestID),
    FOREIGN KEY (ReceiverID) REFERENCES Employees(EmployeeID)
);
GO
-------nếu leavetype cần giấy tờ chứng nhận tải lên bất kỳ tài liệu hỗ trợ nào xin nghỉ phép lâu dài ứng dụng sẽ lưu một bản ghi vào bảng LeaveDocuments, bao gồm cả đường dẫn đến tài liệu.
DROP TABLE IF EXISTS LeaveDocuments
CREATE TABLE LeaveDocuments (
    DocumentID INT IDENTITY(1,1) PRIMARY KEY,
    LeaveRequestID INT NOT NULL,
	Image NVARCHAR(250),
    DocumentPath NVARCHAR(255),
    UploadedDate DATETIME NOT NULL DEFAULT GETDATE(),
	Status BIT NOT NULL DEFAULT 1,
    FOREIGN KEY (LeaveRequestID) REFERENCES LeaveRequests(LeaveRequestID)
);
GO


----------------------PROC-------------------------------

-- Check user login
CREATE OR ALTER PROC selectLogin
@Email NVARCHAR(100),
@Password NVARCHAR(100)
AS
BEGIN
SELECT 
    *
FROM 
    Employees

WHERE 
    Email = @Email AND Password = @Password;

END
GO
	
--- select Role by id emp
CREATE OR ALTER PROC selectRole
@idEmp Int
AS
BEGIN
SELECT 
    r.RoleName
FROM  Roles r
Join EmployeeRoles er on r.RoleID = er.RoleID
WHERE er.EmployeeID = @idEmp
    

END
GO

--- select Departments by id emp
CREATE OR ALTER PROC selectDepartments
@idEmp Int
AS
BEGIN
SELECT 
    d.DepartmentName, d.DepartmentID
FROM  Departments d
Join Positions p  on p.DepartmentID = d.DepartmentID
Join Employee_positions ep on ep.PositionID = p.PositionID
WHERE ep.EmployeeID = @idEmp
    

END
GO

--- select LeaveRequests by id emp
CREATE OR ALTER PROC selectLeaveRequest
@idEmp Int
AS
BEGIN
SELECT 
    *
FROM  LeaveRequests
WHERE EmployeeID = @idEmp
    

END
GO

--- select LeaveType by id leaveRequest
CREATE OR ALTER PROC selectLeaveTypeByLr
@idLeaveRequest Int
AS
BEGIN
SELECT 
    lt.*
FROM  LeaveTypes lt
join LeaveRequests lr on lr.LeaveTypeID =   lt.LeaveTypeID
WHERE lr.LeaveRequestID = @idLeaveRequest
  

END
GO

CREATE OR ALTER PROC selectLeaveType
@idLeaveType Int
AS
BEGIN
SELECT 
    *
FROM  LeaveTypes 
WHERE LeaveTypeID = @idLeaveType
  

END
GO

-- select count id all of statusLR in ('Submitted', 'Viewed by Lead') for (leader) 
create or alter proc countLRforLeader
@empID int 
as
begin
	select count(LeaveRequestID) total from LeaveRequests lr
	Join Employee_positions ep on ep.EmployeeID = lr.EmployeeID
	Join Positions p on p.PositionID = ep.PositionID
	Join Departments d on d.DepartmentID = p.DepartmentID 

	where StatusLR in  ('Submitted','Viewed by Lead') and d.DepartmentID = (SELECT d.DepartmentID
							FROM  Departments d
							Join Positions p  on p.DepartmentID = d.DepartmentID
							Join Employee_positions ep on ep.PositionID = p.PositionID
							WHERE ep.EmployeeID = @empID)
	
end
go

-- Phan trang cho gui dơn ('Submitted','Viewed by Lead') của Leader
create or alter proc selLRequestForLeader
@empID int ,
@pageNumber int,
@RowOfPage Int
as
begin
	Select  lr.*
	from LeaveRequests lr
	Join Employee_positions ep on ep.EmployeeID = lr.EmployeeID
	Join Positions p on p.PositionID = ep.PositionID
	Join Departments d on d.DepartmentID = p.DepartmentID
	Where lr.StatusLR in ('Submitted','Viewed by Lead')  and  d.DepartmentID = (SELECT 
																						d.DepartmentID
																					FROM  Departments d
																					Join Positions p  on p.DepartmentID = d.DepartmentID
																					Join Employee_positions ep on ep.PositionID = p.PositionID
																					WHERE ep.EmployeeID = @empID)
	order by lr.LeaveRequestID
		offset (@pageNumber - 1)*@RowOfPage rows 
		fetch next @RowOfPage rows only 
end
go

-- select count id all of statusLR in ('Approved by Lead', 'Viewed by HR') for (admin) 
create or alter proc countLRforAdmin
@empID int 
as
begin
	select count(LeaveRequestID) total from LeaveRequests lr
	Join Employee_positions ep on ep.EmployeeID = lr.EmployeeID
	Join Positions p on p.PositionID = ep.PositionID
	Join Departments d on d.DepartmentID = p.DepartmentID 
	where StatusLR in  ('Approved by Lead', 'Viewed by HR')
	
end
go

-- Phan trang cho gui dơn ('Approved by Lead', 'Viewed by HR') của Admin
create or alter proc selLRequestForAdmin
@empID int ,
@pageNumber int,
@RowOfPage Int
as
begin
	Select  lr.*
	from LeaveRequests lr
	Join Employee_positions ep on ep.EmployeeID = lr.EmployeeID
	Join Positions p on p.PositionID = ep.PositionID
	Join Departments d on d.DepartmentID = p.DepartmentID
	Where lr.StatusLR in ('Approved by Lead', 'Viewed by HR')  
	order by lr.LeaveRequestID
		offset (@pageNumber - 1)*@RowOfPage rows 
		fetch next @RowOfPage rows only 
end
go

-- select all Employee or select by id
create or alter proc selectEmpID
	@EmployeeID INT = null

as
begin

	if	@EmployeeID is not null
		SELECT *
		FROM Employees 
		WHERE EmployeeID = @EmployeeID ;
	else
		SELECT *
		FROM Employees ;
end
go

-- Change confirmation or rejection status
create or alter proc updateStatusLR
	@statusLR varchar(20) = null,
	@approveId int = null,
	@leaveRequestId int = null
	
as
begin
		UPDATE LeaveRequests
		SET StatusLR = @statusLR,
		ApprovalDate = GETDATE(),
		ApproverID = @approveId
		WHERE LeaveRequestID = @leaveRequestId;
end
go

-- Insert leaveType
create or alter proc insertleaveType
@leaveTypeName VARCHAR(100),
@leaveTypeDescription TEXT,
@leaveDaysPerYear int
as
begin
	Insert into LeaveTypes(LeaveTypeName,LeaveTypeDescription,LeaveDaysPerYear)
	values (@leaveTypeName,@leaveTypeDescription,@leaveDaysPerYear)
end
go


-- Update leaveType
create or alter proc updateleaveType
@leaveTypeName VARCHAR(100),
@leaveTypeDescription TEXT,
@leaveDaysPerYear int,
@id int
as
begin
		Update LeaveTypes
		set leaveTypeName =@leaveTypeName,
		LeaveTypeDescription = @leaveTypeDescription,
		LeaveDaysPerYear = @leaveDaysPerYear
		Where LeaveTypeID = @id
end
go




-- select count id all of statusLR in ('Approved by Lead', 'Rejected') for (leader) 
create or alter proc countLHforLeader
@empID int 
as
begin
	select count(LeaveRequestID) total from LeaveRequests lr
	Join Employee_positions ep on ep.EmployeeID = lr.EmployeeID
	Join Positions p on p.PositionID = ep.PositionID
	Join Departments d on d.DepartmentID = p.DepartmentID 

	where StatusLR in  ('Approved by Lead', 'Rejected') and d.DepartmentID = (SELECT d.DepartmentID
							FROM  Departments d
							Join Positions p  on p.DepartmentID = d.DepartmentID
							Join Employee_positions ep on ep.PositionID = p.PositionID
							WHERE ep.EmployeeID = @empID)
	
end
go

-- Phan trang cho gui dơn ('Approved by Lead', 'Rejected') của Leader
create or alter proc selLHistoryForLeader
@empID int ,
@pageNumber int,
@RowOfPage Int
as
begin
	Select  lr.*
	from LeaveRequests lr
	Join Employee_positions ep on ep.EmployeeID = lr.EmployeeID
	Join Positions p on p.PositionID = ep.PositionID
	Join Departments d on d.DepartmentID = p.DepartmentID
	Where lr.StatusLR in ('Approved by Lead', 'Rejected')  and  d.DepartmentID = (SELECT 
																						d.DepartmentID
																					FROM  Departments d
																					Join Positions p  on p.DepartmentID = d.DepartmentID
																					Join Employee_positions ep on ep.PositionID = p.PositionID
																					WHERE ep.EmployeeID = @empID)
	order by lr.LeaveRequestID
		offset (@pageNumber - 1)*@RowOfPage rows 
		fetch next @RowOfPage rows only 
end
go

-- select count id all of statusLR in ('Approved by HR', 'Rejected') for (Admin) 
create or alter proc countLHforAdmin
@empID int 
as
begin
	select count(LeaveRequestID) total from LeaveRequests lr
	Join Employee_positions ep on ep.EmployeeID = lr.EmployeeID
	Join Positions p on p.PositionID = ep.PositionID
	Join Departments d on d.DepartmentID = p.DepartmentID 

	where StatusLR in  ('Approved by HR', 'Rejected')
	
end
go

-- Phan trang cho gui dơn ('Approved by HR', 'Rejected') của Admin
create or alter proc selLHistoryForAdmin
@empID int ,
@pageNumber int,
@RowOfPage Int
as
begin
	Select  lr.*
	from LeaveRequests lr
	Join Employee_positions ep on ep.EmployeeID = lr.EmployeeID
	Join Positions p on p.PositionID = ep.PositionID
	Join Departments d on d.DepartmentID = p.DepartmentID
	Where lr.StatusLR in ('Approved by HR', 'Rejected') 
	order by lr.LeaveRequestID
		offset (@pageNumber - 1)*@RowOfPage rows 
		fetch next @RowOfPage rows only 
end
go

-- delete 1 row for table by id 

create or alter proc delete1rowLR
@Id int

as
begin
	DELETE FROM LeaveRequests WHERE LeaveRequestID = @Id;
end
go

--select all Leave Type 
create or alter proc getAllLeaveType
as
begin
	Select * from LeaveTypes
end
go

-- select Employee by DeparmentId and RoleName
create or alter proc selEmpbyIdDeparment
@IdDepar int,
@roleName nvarchar(15)
as
begin
	if @roleName is not null 
		SELECT e.*
		FROM Employees e
		JOIN Employee_positions ep ON e.EmployeeID = ep.EmployeeID
		JOIN Positions p ON ep.PositionID = p.PositionID
		JOIN EmployeeRoles er ON e.EmployeeID = er.EmployeeID
		Join Roles r on er.RoleID = r.RoleID
		WHERE p.DepartmentID = @IdDepar AND r.RoleName = @roleName
	else 
		SELECT e.*
		FROM Employees e
		JOIN Employee_positions ep ON e.EmployeeID = ep.EmployeeID
		JOIN Positions p ON ep.PositionID = p.PositionID
		JOIN EmployeeRoles er ON e.EmployeeID = er.EmployeeID
		Join Roles r on er.RoleID = r.RoleID
		WHERE p.DepartmentID = @IdDepar
end
go 

-- select employee role admin
create or alter proc selEmpAdmin
@roleName VARCHAR(50)
as
begin
	SELECT e.*
	FROM Employees e
	JOIN EmployeeRoles er ON e.EmployeeID = er.EmployeeID
	Join Roles r on er.RoleID = r.RoleID
	WHERE r.RoleName =  @roleName
end
go

-- Select all employees on leave this day
create or alter proc selEmpDay
as
begin
SELECT 
    StartDate, EndDate,EmployeeID,Reason,LeaveTypeID
FROM 
    LeaveRequests 
WHERE 
   StartDate <= GETDATE() 
    AND EndDate >= GETDATE() 
    AND StatusLR IN ('Approved by HR');

end
go

-- Select all employees on leave this week
create or alter proc selEmpWeek
as
begin
SELECT 
    StartDate, EndDate,EmployeeID,Reason,LeaveTypeID
FROM 
    LeaveRequests 
WHERE 
    DATEPART(week,StartDate) = DATEPART(week, GETDATE()) -- Lấy tuần hiện tại
    AND YEAR(StartDate) = YEAR(GETDATE()) -- Trong năm hiện tại
    AND StatusLR IN ('Approved by HR');

end
go

-- Select all employees on leave this month
create or alter proc selEmpMonth
as
begin
SELECT 
    StartDate, EndDate,EmployeeID,Reason,LeaveTypeID
FROM 
    LeaveRequests 
WHERE 
    DATEPART(MONTH,StartDate) = DATEPART(MONTH, GETDATE()) -- Lấy tuần hiện tại
    AND YEAR(StartDate) = YEAR(GETDATE()) -- Trong năm hiện tại
    AND StatusLR IN ('Approved by HR');

end
go



-- Count employee leave this week
create or alter proc countEmpWeek
as
begin
SELECT COUNT(DISTINCT employeeId) AS total
FROM LeaveRequests
WHERE DATEPART(YEAR, startDate) = DATEPART(YEAR, GETDATE())
AND DATEPART(WEEK, startDate) = DATEPART(WEEK, GETDATE())
And StatusLR IN ('Approved by HR');

end
go

--Count employee leave this month
create or alter proc countEmpMonth
as
begin
SELECT COUNT(DISTINCT employeeId) AS total
FROM LeaveRequests
WHERE DATEPART(YEAR, startDate) = DATEPART(YEAR, GETDATE())
AND DATEPART(MONTH, startDate) = DATEPART(MONTH, GETDATE())
And StatusLR IN ('Approved by HR');

end
go



--Count employee leave this month
create or alter proc countEmpDay
as
begin
SELECT COUNT(DISTINCT employeeId) AS total
FROM LeaveRequests
WHERE CAST(startDate AS DATE) = CAST(GETDATE() AS DATE)
And StatusLR IN ('Approved by HR');

end
go

-- select all Employee leave this day/month/year


create or alter proc GetEmployeesOnLeaveByDate
    @SelectedDate DATE
AS
BEGIN
    SELECT 
         StartDate, EndDate,EmployeeID,Reason,LeaveTypeID
    FROM 
        LeaveRequests
    WHERE 
        @SelectedDate BETWEEN LeaveRequests.StartDate AND LeaveRequests.EndDate;
END
go 

CREATE PROCEDURE GetNotificationsByEmployee
    @EmployeeID INT
AS
BEGIN
    -- Select notifications for the specified employee
    SELECT NotificationID, LeaveRequestID, NotificationDate, ReceiverID, Message, Status
    FROM Notifications
    WHERE ReceiverID = @EmployeeID
    ORDER BY Status DESC, NotificationDate DESC;
END
EXEC GetNotificationsByEmployee @EmployeeID = 1;

SELECT * FROM Notifications WHERE ReceiverID = 1;

-- Insert data for table LeaveRequests 
create or alter proc InsertLeaveRequest
@employeeId int,
@leaveTypeId int,
@startDate Date,
@endDate Date,
@reason NVARCHAR(255),
@statusLR VARCHAR(20) 
AS
BEGIN
    INSERT INTO LeaveRequests (EmployeeID, LeaveTypeID, StartDate, EndDate, Reason, StatusLR)
	VALUES
	(@employeeId, @leaveTypeId, @startDate, @endDate, @reason, @statusLR)

	SELECT SCOPE_IDENTITY() AS LeaveRequestID;
END
go

--update leaveRequest by id
create or alter proc updateLeaveRequest

@leaveTypeId nvarchar(50), 
@startDate date,
@endDate date,
@reason nvarchar(250),
@LeaveRequestId int
as
begin
	Update LeaveRequests
	set LeaveTypeID = @leaveTypeId , StartDate = @startDate, EndDate = @endDate, Reason = @reason, SubmissionDate = GETDATE()
	where  LeaveRequestID = @LeaveRequestId
end
go

-- đém tất cả số ngày nghỉ của loại nghỉ đó đã dùng
create or alter proc selAllLeaveRequestByIdLT

@EmployeeID int ,
 @LeaveTypeID int
as
begin
	SELECT 
		ISNULL(SUM(DATEDIFF(DAY, StartDate, EndDate) + 1), 0) AS TotalUsedDays
	FROM 
		LeaveRequests
	WHERE 
		EmployeeID = @EmployeeID
		AND LeaveTypeID = @LeaveTypeID
		AND YEAR(StartDate) = YEAR(GETDATE())
		AND StatusLR IN ('Approved by HR');
end
go

-- lấy tất cả các đơn của những ngày tiếp theo
create or alter proc selAllLeaveRequestNextDay
@EmployeeID int
as
begin
	SELECT lr.EndDate, lr.StartDate
	FROM LeaveRequests lr
	JOIN Employees e ON lr.EmployeeID = e.EmployeeID
	JOIN LeaveTypes lt ON lr.LeaveTypeID = lt.LeaveTypeID
	WHERE lr.StartDate >= GETDATE() and e.EmployeeID = 5
	ORDER BY lr.StartDate;
end
go

-- insert leave Documents
create or alter proc InsertLeaveDocument
    @LeaveRequestID INT,
    @DocumentPath NVARCHAR(255)
AS
BEGIN
    INSERT INTO LeaveDocuments (LeaveRequestID, DocumentPath, UploadedDate, Status)
    VALUES (@LeaveRequestID, @DocumentPath, GETDATE(), 1);
END;
GO

-- insert Notification

create or alter proc InsertNotification
    @LeaveRequestID INT,
    @ReceiverID INT,
    @Message NVARCHAR(255)
AS
BEGIN
    INSERT INTO Notifications (LeaveRequestID, NotificationDate, ReceiverID, Message, Status)
    VALUES (@LeaveRequestID, GETDATE(), @ReceiverID, @Message, 1);
END;
GO

-- select leave Documents by idLeaverequest
create or alter proc selLeaveDocument
    @LeaveRequestID INT
AS
BEGIN
    select * 
	from LeaveDocuments
	where LeaveRequestID = @LeaveRequestID
END;
GO

--update leave Documents by id
create or alter proc updateLeaveDocument
@documentPath nvarchar(255),
@LeaveRequestId int
as
begin
	Update LeaveDocuments
	set DocumentPath = @documentPath ,UploadedDate = GETDATE()
	where  LeaveRequestID = @LeaveRequestId
end
go


select * from LeaveRequests where EmployeeID = 5