package com.uuabb.miao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MiaoApplicationTests {

    @Test
    public void contextLoads() {
        String s1 = "Programming";
        String s2 = new String("Programming");
        String s3 = "Program" + "ming";
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s1 == s2.intern());

        try {
            try {
                throw new Sneeze();
            }
            catch ( Annoyance a ) {
                System.out.println("Caught Annoyance");
                throw a;
            }
        }
        catch ( Sneeze s ) {
            System.out.println("Caught Sneeze");
            return ;
        }
        finally {
            byte[] a=new byte[]{1,2,3,4,5};
            System.out.println("a-- "+a);
            System.out.println("Hello World!");
        }

    }


}
class Annoyance extends Exception {}
class Sneeze extends Annoyance {}
