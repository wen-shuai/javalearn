# Java 2023 春季学习
some changes
some changes two
### 一.薄弱点

1. ##### jdk、jre、jvm区别

   java源代码是filename.java文件，通过 **jdk java** 开发工具包对其进行编译，形成filename.class字节码文件，而**jre**中包含了一些系统库以及java程序运行环境，如jvm。jvm是java虚拟机进程，可以将字节码文件转换为对应机器的指令代码，所以java程序代码只需要编写一遍。

2. ##### 值传递与引用传递

   java当中全部都是值传递。对于基本类型，值传递。引用类型时，其实传递的是引用类型地址的一个副本，所以看起来像是引用传递，事实上是值传递。可以参考掘金关注。

3. ##### static关键字

   ​		**在了解java中的static之前，有必要知道java程序的加载过程。java程序在编译形成字节码文件之后，首先会把main函数所在的类加载到内存中（<u>*在java中，要想使用一个类的静态变量或者静态函数，必须先把这个类加载到内存中，加载不是初始化，加载的是类，初始化的是对象*</u>），然后初始化这个类当中的static变量，然后执行static main（）方法，在一个类被调用或者初始化时，也会遵循上述规则。当一个已经被加载在内存中的类没有任何一个实例，也没有静态方法、变量的调用时，类会被卸载，即清出内存。**

   [参考地址](https://blog.csdn.net/yabay2208/article/details/71171207)

   类加载、初始化过程：先类加载到内存中，初始化静态变量并加载到栈中，运行静态代码块、运行非静态代码块，运行初始化方法。

   区分静态变量、静态方法、静态代码块、静态内部类。其中静态内部类可用于单例模式：

   ```java
   public class Singleton {
       private Singleton() {}
   
       private static class SingletonHolder {
           public static final Singleton instance = new Singleton();
       }
   
       public static Singleton getInstance() {
           return SingletonHolder.instance;
       }
   }
   作者：沉默王二
   链接：https://juejin.cn/post/6844904180365131783
   来源：稀土掘金
   ```

4. 成员内部类、局部内部类、匿名内部类和静态内部类

   + 成员内部类：相当于外部类的一个属性，外部类先加载，内部类才能加载，内部类持有外部类的引用，内部类应当借助外部类进行申请，如

     ```java
     Body.Heart heart = new Heart();
     ```

      另外，非静态内部类中不应当有静态变量和方法。

   + 局部内部类： 在外部类某一个方法内定义的类，不能用static、修饰符。是否持有外部类的引用视情况而定。

   + 匿名内部类： 若此类只用于某个特定的地方，即可使用匿名内部类简化开发，同样是不能有static、访问修饰符。

   + 静态内部类： java中只有内部类允许是静态的，事实上静态内部相当于一个独立的类，只是逻辑上属于外部类，静态内部类不持有外部类的应用，只可以访问外部类的静态变量，若要访问外部类的变量，则应通过实例的方式，即传递类地址值。

     [参考链接1](https://blog.csdn.net/yabay2208/article/details/71171207)          [参考链接2](https://www.runoob.com/w3cnote/java-inner-class-intro.html#:~:text=%E5%8C%BF%E5%90%8D%E5%86%85%E9%83%A8%E7%B1%BB%E6%98%AF%E5%94%AF,%E5%AE%9E%E7%8E%B0%E6%88%96%E6%98%AF%E9%87%8D%E5%86%99%E3%80%82)

5. java中override和overload之间的别

   override是重写，即子类对父类方法的重写，要注意的是，重写方法不能抛出新的检查异常或者比被重写方法申明更加宽泛的异常。例如： 父类的一个方法申明了一个检查异常 IOException，但是在重写这个方法的时候不能抛出 Exception 异常，因为 Exception 是 IOException 的父类，抛出 IOException 异常或者 IOException 的子类异常。在面向对象原则里，重写意味着可以重写任何现有方法。利用super.move()可以调用父类的move（）方法。

   ```java
   class Animal{
      public void move(){
         System.out.println("动物可以移动");
      }
   }
    
   class Dog extends Animal{
      public void move(){
         System.out.println("狗可以跑和走");
      }
   }
    
   public class TestDog{
      public static void main(String args[]){
         Animal a = new Animal(); // Animal 对象
         Animal b = new Dog(); // Dog 对象
    
         a.move();// 执行 Animal 类的方法
    
         b.move();//执行 Dog 类的方法
      }
   }
   ```

   在上面的例子中可以看到，尽管 b 属于 Animal 类型，但是它运行的是 Dog 类的 move方法。

   这是由于在编译阶段，只是检查参数的引用类型。

   然而在运行时，Java 虚拟机(JVM)指定对象的类型并且运行该对象的方法。

   因此在上面的例子中，之所以能编译成功，是因为 Animal 类中存在 move 方法，然而运行时，运行的是特定对象的方法。

   ```
   class Animal{
      public void move(){
         System.out.println("动物可以移动");
      }
   }
    
   class Dog extends Animal{
      public void move(){
         System.out.println("狗可以跑和走");
      }
      public void bark(){
         System.out.println("狗可以吠叫");
      }
   }
    
   public class TestDog{
      public static void main(String args[]){
         Animal a = new Animal(); // Animal 对象
         Animal b = new Dog(); // Dog 对象
    
         a.move();// 执行 Animal 类的方法
         b.move();//执行 Dog 类的方法
         b.bark();
      }
   }
   ```

   该程序将抛出一个编译错误，因为b的引用类型Animal没有bark方法。

   overload是同一个类里对同一个方法名进行多写。

6. 关于java中继承的几个特性

   java中的继承使用的关键字为extends和implements。其中extends继承是类之间的继承，子类默认继承了父类非private属性和方法，父类属性可以直接用，父类方法可以重写，可以扩展自己的属性和方法。java当中并不支持多继承，即一个类继承多个类，但支持多重继承，即A继承B，B又继承C。

   在构造一个子类时，必须先构造一个父类，所以在子类构造方法中，会自动隐式调用父类无参构造方法，如果在子类构造方法中，显示的调用了父类的有参构造，则不会再自动隐式调用父类的无参构造方法。子类并不会继承父类的构造方法，而是用super(int a);对父类参数进行初始化。所以子类要使用父类的参数时，必须通过子类构造函数调用父类构造函数，对父类进行初始化。

   ```java
   class SuperClass {
     private int n;
     SuperClass(){
       System.out.println("SuperClass()");
     }
     SuperClass(int n) {
       System.out.println("SuperClass(int n)");
       this.n = n;
     }
   }
   // SubClass 类继承
   class SubClass extends SuperClass{
     private int n;
     
     SubClass(){ // 自动调用父类的无参数构造器
       System.out.println("SubClass");
     }  
     
     public SubClass(int n){ 
       super(300);  // 调用父类中带有参数的构造器
       System.out.println("SubClass(int n):"+n);
       this.n = n;
     }
   }
   // SubClass2 类继承
   class SubClass2 extends SuperClass{
     private int n;
     
     SubClass2(){
       super(300);  // 调用父类中带有参数的构造器
       System.out.println("SubClass2");
     }  
     
     public SubClass2(int n){ // 自动调用父类的无参数构造器
       System.out.println("SubClass2(int n):"+n);
       this.n = n;
     }
   }
   public class TestSuperSub{
     public static void main (String args[]){
       System.out.println("------SubClass 类继承------");
       SubClass sc1 = new SubClass();
       SubClass sc2 = new SubClass(100); 
       System.out.println("------SubClass2 类继承------");
       SubClass2 sc3 = new SubClass2();
       SubClass2 sc4 = new SubClass2(200); 
     }
   }
   结果
   ------SubClass 类继承------
   SuperClass()
   SubClass
   SuperClass(int n)
   SubClass(int n):100
   ------SubClass2 类继承------
   SuperClass(int n)
   SubClass2
   SuperClass()
   SubClass2(int n):200
   ```

   还要注意，final代表最终的意思，如果是final class则不能被继承；如果是final 方法，则不能重写（public/private final）。

7. java中的接口注意事项

   @override注解作用：只能用来修饰方法，告诉编译器此方法一定是重写父类某一方法， 若没有重写父类的某一方法，则直接报错，用于避免一些低级错误。
   
   








### 二. Git及Github学习记录

1. 创建、推送流程：

   + 先在本地生成一个ssh秘钥：

     ```bash
     ssh-keygen -t rsa -C "your_email@youremail.com"
     ```

     后面的`your_email@youremail.com`改为你在github上注册的邮箱，之后会要求确认路径和输入密码，我们这使用默认的一路回车就行。成功的话会在`~/`下生成`.ssh`文件夹，进去，打开`id_rsa.pub`，复制里面的`key`（即C盘下面的user用户里面）。回到github上，进入 Account Settings（账户配置），左边选择SSH Keys，Add SSH Key,title随便填，粘贴在你电脑上生成的key。验证是否成功，

     ```
     $ ssh -T git@github.com
     ```

     如果是第一次的会提示是否continue，输入yes就会看到：You've successfully authenticated, but GitHub does not provide shell access 。这就表示已成功连上github。接下来我们要做的就是把本地仓库传到github上去，在此之前还需要设置username和email，因为github每次commit都会记录他们。

     ```bash
     定制所有仓库提交时的个人信息，不加--global则是对此仓库起效
     $ git config --global user.name "yourname"
     $ git config --global user.email "youremail"
     
     ```

     添加远程仓库，也可以直接打开.git文件夹下config文本里面修改remote。

     ```bash
     #这里的url指的就是远程仓库的ssh地址，git remote add的意思就是，给url起个别名，叫origin
     #之后在用到url繁长的需要时都可以用origin来代替，
     git remote add origin url
     ```

     提交流程：

     ```bash
      #这里切记一定要去到根目录下面，即能够看到.git文件。否则提交不上

     $ git add .   git add *.java等
     $ git commit -m 'message'
     $ git push origin master   其中master是分支名，可用git checkout othermaster切换分支
     #注意，git push失败是有成本的，如果push失败，commit版本会被退回，之后再commit不会覆盖！！
     ```

2. 下载、下拉、分支合并、冲突解决流程

   下载指令：

   ```bash
   git clone url
   不需要在这之前或者之后git init
   ```

   fetch + merge = pull 指令

   ```bash
   #该指令用于从远程仓库拉取本地没有的数据
   $git fetch origin
   #该指令将远程分支提取更新到当前分支
   $git merge origin/master
   
   $ git pull origin master
   ```
   ```
   git diff指令详解：
   #显示工作区和暂存区的差异：
   git diff filename
   
   #显示暂存区和上一次提交（head区）的差异：
   git diff --cached filename
   
   #显示工作区和版本库（head区）的差异：
   git diff HEAD filename
   
   #显示两次提交之间的差异：没看懂
   $ git diff [first-branch]...[second-branch]
   ```

   版本回退指令：

   ```bash
   #查看历史版本
   git reflog
   
   #回退版本
   git reset --hard 序号
   ```

​    分支基本操作：

```bash
创建分支：
git branch -b branchname
创建并直接切换分支
git checkout -b branchname
切换分支
git checkout branchname

删除分支：
git branch -d branchname

直接分支合并，合并之后还需要add-commit
git merge branchname
如果merge失败，需要手动去打开冲突的文件，有------->head 的提示
									==========
									<<<<<<<<
									
合并并直接提交，可以插入合并信息：（更为推荐）
git merge --no-ff -m "modify readme.md with no-ff" A
```

接管分支操作：

```
在对文件进行修改时，突然被要求接管其他分支开发，但此时自己的branch暂时不能commit，不能进行分支切换，可以通过git stash隐藏本分支的工作现场，之后切回本分支的时候，利用git stash pop进行恢复。注意如果是新建文件的话一定要add，注意这里的文件问题！！暂时不懂，可以参考github。
```

多人协同开发：

一般来说，master是用来确定最终版本，用来上线的，用作开发的一般是dev分支。现在具体讲讲怎么多人在线协同。首先每个人都应该在自己的环境中确定一个dev分支，可以为这个分支直接绑定远程分支：

```
git branch --set-upstream-to=origin/dev dev
```

也可以不绑定，但每次push或者pull的时候都需要指定远程分支。在某一位同事第一次push dev分支的时候，远程github会自动创建一个分支。然后每个同事都把远程dev分支clone或者pull下来，最好是pull。然后创建一个dev分支即可进行开发。当出现冲突时，需要手动pull一下，在本地进行合并，如果不能自动合并，则需要手动打开进行合并，然后再add-commit-push。最好是在开发之前，先pull一下。

上线操作也是一样的，利用将远程devpull到本地dev，再切换到本地master合并本地dev，在将本地master push即可。

完结！！！

[参考github链接，非常详细](https://github.com/Masterpaopao/Git-And-Github/blob/master/Git%E9%AB%98%E9%98%B6/Git%E9%AB%98%E9%98%B6%E5%AD%A6%E4%B9%A0.md)

