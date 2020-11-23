import java.io.IOException;
import java.time.*;
import java.util.ArrayList;

/**
 * Control class to handle all staff related functions.
 */
public class StaffController{

	/**
	 * Change the access time for students
	 * @param matricNum	Matriculation number of Student whose access period is to be edited.
	 * @param startTime Start time that the Student can access MySTARS system.
	 * @param endTime 	End time after which the Student can no longer access MySTARS system.
	 */
	public static void editStudentAccessPeriod(String matricNum, LocalDateTime start, LocalDateTime end) { //tested
		try {
	        ArrayList<Student> studentList = StudentManager.extractDB();
	        for (Student s: studentList) { 
        		if(s.getMatricNum().equals(matricNum)) {
        			System.out.println("Found student");
        			s.setStartAccessTime(start);
        			s.setEndAccessTime(end);
        			System.out.println("Student " + s.getName() + " can only access the portal from "+
        						start + " to " + end);  
        			StudentManager.updateStudentDB(studentList);
        			break;
        		
        		}
	        	
	        }
		}
		catch(Exception e) {
			System.out.println("File not found");
			
		}
	}

	/**
	 * Print all the information of a specific student.
	 * @param matricsNum 				Matriculation number of Student whoes details is to be printed
	 * @throws IOException 				File does not exist.
	 * @throws ClassNotFoundException 	Mentioned classes are not found in the
	 */
	public static void printSelectedStudent(String matricNum) throws ClassNotFoundException, IOException {
		ArrayList<Student> studentList = StudentManager.extractDB();
		for (Student s: studentList) {
			if(s.getMatricNum().equals(matricNum)) {
				System.out.printf("Student name: " + s.getName() +" Matric Number: " + s.getMatricNum() +  " Gender: " + 
						 " Nationality: " + s.getNationality() + " Year of Study: " + s.getYearOfStudy()+  " Access time from " + s.getStartAccessTime() + " to " + s.getEndAccessTime() +"\n");
				StudentController.printCourseRegistered(s);
				break;
			}
		}
	}

	/**
	 * Add a new student into the database.
	 * @param username 			Student's username.
	 * @param password 			Student's raw password (unhashed).
	 * @param name 				Student's full name.
	 * @param matricNum 		Student's matriculation number.
	 * @param gender 			Student's gender.
	 * @param nationality 		Student's nationality.
	 * @param yearOfStudy		Student's current year of study.
	 * @param startAccessTime 	Start time that Student can access MySTARS sys.tem
	 * @param endAccessTime 	End time after which Student cannot access MySTARS system.
	 */
	public static void addStudent(String username, String password, String name,  //tested
    		String matricNum, char gender, String nationality, 
    		int yearOfStudy, LocalDateTime startAccessTime, LocalDateTime endAccessTime) {
		Student student = new Student(username, password, name, matricNum, gender, nationality, 
										yearOfStudy, startAccessTime, endAccessTime);
		try {
			StudentManager.addStudent(student);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(Exception e) {
			System.out.println("There was an error");
		}
	}
	
	/**
	 * Add new course into the Course database.
	 * @param courseCode 	Course code of the Course to be added
	 * @param courseName 	Name of the Course to be added
	 * @param numAUs 		Number of AUs of the Course to be added.
	 * @param indexList 	ArrayList of Indexes that the Course has.
	 */
	public static void addCourse(String courseCode, String courseName, String school, int numAUs, ArrayList<Index> newIndexlist) { //Tested
		Course newCourse = new Course(courseCode, courseName, school, numAUs, newIndexlist);
		try {
			CourseManager.addCourse(newCourse);
		} catch (ClassNotFoundException e) {		
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();		}
		catch(Exception e) {
			System.out.println("There was an error");
		}
		printSingleCourse(newCourse.getCourseCode());
	}
	
	/**
	 * Update an existing course. If certain attribute is unchanged, the current value is passed in as the new value.
	 * @param oldCode 		Course's ld course code.
	 * @param newCode 		Course's new course code.
	 * @param newCourseName Course's new name.
	 * @param newAUS 		Course's new number of AUs.
	 * @param newIndexlist 	Course's new ArrayList of Indexes.
	 */
	public static void updateCourse(String oldCode, String newCode, String newCourseName, int newAUS, ArrayList<Index> newIndexlist) throws IOException {
		//TODO
		ArrayList<Course> courseList = CourseManager.extractDB();
		for(Course c : courseList){
			if(c.getCourseCode().equals(oldCode)) {
				c.setCourseCode(newCode);
				c.setNumAUs(newAUS);
				c.setIndexList(newIndexlist);
				c.setCourseName(newCourseName);
				CourseManager.UpdateDB(courseList);
			}
		}
	}

	/**
	 * Add new Index to a Course.
	 * @param courseCode	Course code of the Course to get new Index.
	 * @param newIndex		New Index to be added to the Course.
	 */
	public static void addIndex(String courseCode, Index newIndex) throws IOException {
		//TODO
		ArrayList<Course> courseList = CourseManager.extractDB();
		boolean found = false;
        for (Course c1: courseList) {
        	if (c1.getCourseCode().equals(courseCode)) {
        		ArrayList<Index> indexList = c1.getIndexList();
        		for(Index i: indexList) {
        			if(i.getIndexNum() == newIndex.getIndexNum()){
        				found = true;
        				System.out.println("Index already exists in this course, unable to add the requested Index.");
        			}
        		}
        	}
        }
		if (!found) {
			for (Course c: courseList){
				if (c.getCourseCode().equals(courseCode)) {
					ArrayList<Index> indexList = c.getIndexList();
					indexList.add(newIndex);
					System.out.println("Index successfully added.");
					CourseManager.UpdateDB(courseList);
					break;
				}
			}
		}
	}

	/**
	 * Check for the number of vacancies in an Index.
	 * @param indexNum Index to be checked.
	 * @return the number of vacancies remaining for the Index.
	 */
	public static int checkVacanciesForIndex(int indexNum) throws ClassNotFoundException, IOException {
        ArrayList<Course> courseList = CourseManager.extractDB();
        int vacancies;
        for (Course c : courseList) {
        	ArrayList<Index> indexList = c.getIndexList();
            for(Index i :indexList) {
                if(i.getIndexNum() == indexNum) {
                    vacancies = i.getMaxSize() - i.getNumStudents();
                    return vacancies;
                }
            }
		}
		return 0;
    }

	/**
	 *  Print the list of students that are registered to this Index.
	 * @param IndexNum Index number of the Index to be searched.
	 */
	public static void printStudentByIndex(int indexNum) { 
		//Need to test!!
		System.out.println("+--------------------------------------------------------------------------+");
		System.out.println("| Name                                     | Gender | Nationality          |");
		System.out.println("+--------------------------------------------------------------------------+");		
		try {
	        ArrayList<Student> studentList = StudentManager.extractDB();
	        for (Student s: studentList) { 
	        	ArrayList<Index> registeredIndex = s.getRegisteredIndex();
				//Get the indexArray the student object register
	        	// if(registeredIndex == null) System.out.println("There are no students registered");
	        	for (Index i: registeredIndex) {
	        		if(i.getIndexNum().equals(indexNum)) {
						System.out.printf("| %-40s | %-6s | %-20s |\n", s.getName(), s.getGender(), s.getNationality());
						break;
	        		}
				}
			}
			System.out.println("+--------------------------------------------------------------------------+");
		}
		catch(Exception e) {
			System.out.println("File not found");			
		}
	}
	
	/**
	 *  Print the list of students that are enrolled in this Course.
	 * @param courseCode Course code of the Course to be searched.
	 */
	public static void printStudentByCourse(String courseCode) { 
		System.out.println("+--------------------------------------------------------------------------+");
		System.out.println("| Name                                     | Gender | Nationality          |");
		System.out.println("+--------------------------------------------------------------------------+");		
		try {
	        ArrayList<Student> studentList = StudentManager.extractDB();	        
	        for (Student s: studentList) { 
	        	ArrayList<Index> registeredIndex= s.getRegisteredIndex();        	
	        	for(Index i : registeredIndex) {
	        		// String indexcourseCode = IndexToCourseCode(i);
	        		if(i.getCourseCode().equals(courseCode)) {
						System.out.printf("| %-40s | %-6s | %-20s |\n", s.getName(), s.getGender(), s.getNationality());
						break;
	        		}
	        	}	        	
			}
			System.out.println("+--------------------------------------------------------------------------+");
		}
		catch(Exception e) {
			System.out.println("File not found");
			
		}
	}
	
	// private static String IndexToCourseCode(int IndexNum) {
		
	// 	ArrayList<Course> courseList = CourseManager.extractDB();
	// 	for(Course c: courseList) {
	// 		for(Index i : c.getIndexList()) {
	// 			if(i.getIndexNum() == IndexNum) {
	// 				return i.getCourseCode();
	// 			}
	// 		}
	// 	}
	// 	return "CourseCode not found";
		
	// }

	/**
	 *  Print the list of all Courses in the Course database.
	 */
	public static void printAllCourses() { 
		try {
	        ArrayList<Course> courseList = CourseManager.extractDB();
	        System.out.println("These are the following Courses registered in the course database");
	        for (Course c: courseList) { 
	        	System.out.println(c.getCourseName());
	        	ArrayList<Index> indexList = c.getIndexList();
	        	System.out.println("Indexes registered in this course: ");
	        	for(Index i : indexList){
	        		System.out.println(i.getIndexNum());
	        	}

	        	
	        }
		}
		catch(Exception e) {System.out.println("File not found");}
	}
	
	/**
	 *  Print the details of a selected Course..
	 * @param courseCode Course code of the Course to be printed.
	 */
	public static void printSingleCourse(String courseCode) {
		Course toPrint = null;
		ArrayList<Course> courseList = CourseManager.extractDB();
		for (Course c: courseList) {
			if (c.getCourseCode().equals(courseCode)) {
				toPrint = c;
			}
		}
		if (toPrint == null) {
			System.out.println("Course not found.");
			return;
		}
		System.out.println("+-------------------------------------------------------+");
		System.out.printf( "| Course Name: %-40s |\n", toPrint.getCourseName());
		System.out.printf( "| Course Code: %-40s |\n", toPrint.getCourseCode());
		System.out.printf( "| Host School: %-40s |\n", toPrint.getSchool());
		System.out.printf( "| Number of AUs: %-38s |\n", toPrint.getNumAUs());
		System.out.println("+-------------------------------------------------------+");
		System.out.println("+----------------------------------------------------------------------+");
		System.out.println("| Index | Type       | Venue      | Group | Day | Time         | Weeks |");
		System.out.println("+----------------------------------------------------------------------+");
		for (Index i: toPrint.getIndexList()) {
			for (Lesson l: i.getLessons()) {
				for (Timeslot t: l.getTimeslots()) {
					String time = t.getStartTime().toString() + "-" + t.getEndTime().toString();
					String weeks = null;
					if (t.getWeeks().equals("o")) weeks = "Odd";
					else if (t.getWeeks().equals("e")) weeks = "Even";
					else weeks = "All";
					System.out.printf("| %d | %-10s | %-10s | %-5s | %-3s | %-12s | %-5s |\n",
						i.getIndexNum(), l.getType(), l.getVenue(), l.getGroupId(), dayToString(t.getDay()), time, weeks);
				}
			};
		}
		System.out.println("+----------------------------------------------------------------------+");
	}

	/**
	 * Helper function to convert day input from int to String for printing.
	 * @param day Integer format of the day where 1 - Monday, 7 - Sunday.
	 * @return the day in 3-letter String format (e.g. Mon, Tue, Wed etc.)
	 */
	public static String dayToString(int day) {
		String output = null;
		switch (day) {
			case 1:
				output = "Mon";
				break;
			case 2:
				output = "Tue";
				break;
			case 3:
				output = "Wed";
				break;
			case 4:
				output = "Thu";
				break;
			case 5:
				output = "Fri";
				break;
			case 6:
				output = "Sat";
				break;
			case 7:
				output = "Sun";
				break;
		}
		return output;
	}

	/**
	 * Print number of vacancies remaining for the selected Index.
	 * @param indexNum Index to print vacancies for.
	 * @throws ClassNotFoundException	if mentioned classes are not found in the classpath.
	 * @throws IOException				if file cannot be read in.
	 */
	public static void printVacancies(int indexNum) throws ClassNotFoundException, IOException {
		String courseCode = null;
		String courseName = null;
		ArrayList<Course> courseList = CourseManager.extractDB();
		for (Course c: courseList) {
			for (Index i: c.getIndexList()) {
				if (i.getIndexNum() == indexNum) {
					courseCode = i.getCourseCode();
					courseName = c.getCourseName();
				}
			}
		}
		int vacancies = checkVacanciesForIndex(indexNum);

		System.out.println("+-------------+-----------------------------------------------+--------------+-----------+");
		System.out.println("| Course Code | Course Name                                   | Index Number | Vacancies |");
		System.out.println("+-------------+-----------------------------------------------+--------------+-----------+");
		System.out.printf( "| %-11s | %-45s | %-12s | %-9d |\n", courseCode, courseName, indexNum, vacancies);
		System.out.println("+-------------+-----------------------------------------------+--------------+-----------+");
	}

}