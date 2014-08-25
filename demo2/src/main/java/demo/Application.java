package demo;

import java.util.Calendar;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application implements CommandLineRunner {

	@Autowired
	private StringRedisTemplate template;
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Override
	public void run(String... args) throws Exception {
		
		String key = "test.zset";
		long i = 1;
		while (true) {
			Thread.sleep(5000);
			
			Calendar cal = Calendar.getInstance();
			double time = (double) cal.getTimeInMillis();
			
			Set<String> list = template.opsForZSet().rangeByScore(key, -1, time);
			
			for (String string : list) {
				System.out.println("Found key " + key + ", value=" + string );
			}
			
			
			template.opsForZSet().removeRangeByScore(key,  -1, time);
			
			
			System.out.println("================================" );
			System.out.println("================================" );
			System.out.println("================================" );
		}
	}
}
