```java

package Set_02_workingWithNumbers;

import java.util.Scanner;

public class WWN00_HCF_Finder {
public static void main(String[] args) {
Scanner sc = new Scanner(System.in);

System.out.print("Enter number 1 : ");
int num1 = sc.nextInt();

  

System.out.print("Enter number 2 : ");
int num2 = sc.nextInt();

  

int hcf = 1;
if (num1!=0 && num2!=0){
if (num1==num2){
hcf = num1;
}

else{
int smallNum = num1>num2?num1:num2;
for (int i = 2; i < smallNum; i++) {
if (num1 % i==0 && num2 % i ==0){
hcf=i;
}

}

}

System.out.println("Highest Common Factor [HCF] for "+num1+" and "+num2+" is "+hcf);

}else{

if (num1==0)

System.out.println("Number 1 can't be zero");

  

if (num2==0)

System.out.println("Number 2 can't be zero");

}

  
  

}

}

```

![/home/meghsham.kapure/Documents/MyJava/PrepInsta100Codes/Screenshot from 2025-02-21 10-04-22.png](file:///home/meghsham.kapure/Documents/MyJava/PrepInsta100Codes/Screenshot%20from%202025-02-21%2010-04-22.png)