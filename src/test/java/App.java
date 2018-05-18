import com.yiibai.mybatis.dao.IUser;
import com.yiibai.mybatis.models.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;

public class App {
    private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader;

    static {
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            sqlSessionFactory.getConfiguration().addMapper(IUser.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory getSession() {
        return sqlSessionFactory;
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        // char00 使用xml配置mapper
        //char00();
        char01();
    }

    private static void char00(){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            User user = (User) session.selectOne(
                    "com.yiibai.mybatis.models.UserMapper.GetUserByID", 1);
            if(user!=null){
                String userInfo = "名字："+user.getName()+", 所属部门："+user.getDept()+", 主页："+user.getWebsite();
                System.out.println(userInfo);
            }
        } finally {
            session.close();
        }
    }

    private static void char01(){
        SqlSession session = sqlSessionFactory.openSession();
        try{
            IUser iUser = session.getMapper(IUser.class);
            User user = iUser.getUserByID(1);
            System.out.println("名字："+user.getName());
            System.out.println("所属部门："+user.getDept());
            System.out.println("主页："+user.getWebsite());
        }finally {
            session.close();
        }
    }

}
