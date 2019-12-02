package cn.q.yd;

import cn.q.yd.uitl.Kl;
import org.junit.Test;
import sun.security.krb5.internal.tools.Klist;

public class FindIp {

    @Test
    public void test()
    {
        System.out.println( Kl.sendGetRequest2("1.0.127.255")+ " ");

    }
}
