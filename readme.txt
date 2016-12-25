v1.0
	* 将实体类转换成代码
v1.1
	* 封装数据库连接connection
	* 建立CourseManager,实现添加课程方法
	* 采用单例模式取得CourseManager对象
v1.2
	* 建立CourseController
v1.3
	* 查询课程列表
v1.4
	* 删除课程和修改课程
v1.5
	* 递归打印输出班级列表
v1.6
	* 重构DbUtil
	* 完成添加班级
v1.7
	* 和1.6相同，完成了删除和修改班级的功能
v1.8
	* 建立studentManager接口
	* 完成添加学生
v2.1
	* 分页查询学生
v2.2
	* 建立配置文件，exam-config.properties
	* 将数据库配置信息配置到该配置文件中
	* 完成配置文件的读取
	* 采用反射完成StudentManager的动态实例化
v2.3
	* 建立GradeManager接口
v2.4
	* 修改学生
	* 删除学生
	* 重构StudentController
v2.5
	* 导出学生信息
v2.6
	* 完成添加成绩，修改成绩，删除成绩
	* 动态实例化GradeManagerImpl（new、使用反射、克隆三种方式实现对象实例化）
	* 重构GradeController，采用map传递数据
	* 采用StringTokenizer分解字符串
v2.7
	* 根据学生编号查询学生成绩
	* 注意DecimalFormat的使用
v2.8
	* 分页查询学生成绩信息
	* 重构findGradeListByStudentId方法，将构建Grade列表对象单独拿出来
v2.9
	* 查询总分前三名
	* 取得每科最高分