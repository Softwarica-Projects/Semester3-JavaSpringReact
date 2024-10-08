package np.com.softwarica.castyourvote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication(scanBasePackages = "np.com.softwarica.castyourvote")
@EntityScan(basePackageClasses = {
		CastYourVoteApplication.class,
		Jsr310JpaConverters.class
})
public class CastYourVoteApplication {
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	public static void main(String[] args) {
		SpringApplication.run(CastYourVoteApplication.class, args);
	}
}
