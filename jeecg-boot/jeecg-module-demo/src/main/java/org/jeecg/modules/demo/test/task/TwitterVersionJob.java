package org.jeecg.modules.demo.test.task;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.jeecg.modules.demo.utils.MysqlUtils;
import org.jeecg.modules.demo.utils.TwitterUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TwitterVersionJob  implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }


    public static void main(String[] args) {
        Connection connection = MysqlUtils.getConnection();
        PreparedStatement preparedStatement =
                null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("select twitter_id as twitterId,twitter_name as twitterName,monitor_flag as monitorFlag from twitter_ip_monitor_account");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String twiterId = resultSet.getString("twitterId");
                String twiterName = resultSet.getString("twitterName");
                String monitorFlag = resultSet.getString("monitorFlag");
                if ("Y".equals(monitorFlag)) {
                    System.out.println(twiterName +":" + twiterId);
                    try {


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MysqlUtils.close(resultSet,preparedStatement,connection);
        }


    }
}
