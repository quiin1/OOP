package w2.StudentManagement;

public class StudentManagement {

    // TODO: khai bao thuoc tinh students la array chua cac doi tuong thuoc lop Student (max. 100)
    Student[] students = new Student[100];
    int count = 0;

    /**
     * is 2 students same group.
     */
    public static boolean sameGroup(Student s1, Student s2) {
        // TODO:
        return s1.getGroup().equals(s2.getGroup());
    }

    /**
     * add student.
     */
    public void addStudent(Student newStudent) {
        // TODO:
        if (count < 100) {
            students[count] = newStudent;
        }
        count++;
    }

    /**
     * return a string show list student by group.
     */
    public String studentsByGroup() {
        // TODO:
        boolean[] finished = new boolean[100];
        for (int i = 0; i < 100; i++) {
            finished[i] = false;
        }
        int pos = 0;
        int countFinished = 0;
        StringBuilder res = new StringBuilder();

        while (pos < count && countFinished < count) {
            // ten lop va sinh vien dau tien
            res.append(students[pos].getGroup()).append("\n").append(students[pos].getInfo()).append("\n");
            finished[pos] = true;
            countFinished++;

            // cac sinh vien cung lop
            for (int j = pos + 1; j < count; j++) {

                if (!finished[j] && sameGroup(students[pos], students[j])) {
                    res.append(students[j].getInfo()).append("\n");
                    finished[j] = true;
                    countFinished++;
                }
            }

            for (int i = pos; i < count; i++) {
                if (!finished[i]) {
                    pos = i;
                    break;
                }
            }
        }

        return res.toString();
    }

    /**
     * remove student.
     */
    public void removeStudent(String id) {
        // TODO:
        for (int i = 0; i < count; i++) {
            if (students[i].getId().equals(id)) {
                if (count - 1 - i >= 0) System.arraycopy(students, i + 1, students, i, count - 1 - i);
                count--;
                break;
            }
        }
    }

    public static void main(String[] args) {
        Student student1 = new Student("Nguyen Van An", "17020001", "17020001@vnu.edu.vn");
        student1.setGroup("K62CC");
        Student student2 = new Student("Nguyen Van B", "17020002", "17020002@vnu.edu.vn");
        student2.setGroup("K62CC");
        Student student3 = new Student("Nguyen Van C", "17020003", "17020003@vnu.edu.vn");
        Student student4 = new Student("Nguyen Van D", "17020004", "17020004@vnu.edu.vn");

        StudentManagement uet = new StudentManagement();
        uet.addStudent(student1);
        uet.addStudent(student2);
        uet.addStudent(student3);
        uet.addStudent(student4);
        System.out.println("UET has " + uet.count + " students: ");
        System.out.println(uet.studentsByGroup());
    }
}
