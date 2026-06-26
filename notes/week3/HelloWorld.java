public class HelloWorld {
    public static void main(String[] args) {
        int age = 20;
        double score = 95.5;
        String name = "Java作业";
        if (score >= 60) {
            System.out.println(name + "：成绩及格啦！");
        } else {
            System.out.println(name + "：成绩不及格，需要加油！");
        }
        System.out.println("for循环打印数字：");
        for (int i = 1; i <= 5; i++) {
            System.out.println("数字：" + i);
        }
        printInfo(name, age, score);
    }
    public static void printInfo(String name, int age, double score) {
        System.out.println("\n自定义方法输出：");
        System.out.println("项目名称：" + name);
        System.out.println("年龄：" + age);
        System.out.println("分数：" + score);
    }
}