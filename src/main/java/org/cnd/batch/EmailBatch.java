package org.cnd.batch;

import java.util.List;

import org.cnd.models.Email;
import org.cnd.services.EmailService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * This class is in charge of send the emails that were not sent
 * 
 * @author JParra
 *
 */
@Configuration
public class EmailBatch {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Scheduled(cron = "${batch.email.cron}")
	public void sendEmails() {

	}

	/**
	 * Create the job
	 * 
	 * @return
	 */
	public Job job() {
		return jobBuilderFactory.get("job").incrementer(new RunIdIncrementer()).flow(step1()).end().build();
	}

	/**
	 * Create the step 1
	 * 
	 * @return
	 */
	public Step step1() {
		return stepBuilderFactory.get("step1").<Email, Email>chunk(1).reader(new EmailReaderBatch())
				.processor(new EmailProcessorBatch()).writer(new EmailWriterBatch()).build();
	}

	/**
	 * It contains the reader part of the step
	 * 
	 * @author JParra
	 *
	 */
	public class EmailReaderBatch implements ItemReader<Email> {
		@Autowired
		EmailService emailService;

		@Override
		public Email read() throws Exception {
			List<Email> listEmail = this.emailService.findBySent(Boolean.FALSE);
			if (listEmail != null)
				for (Email email : listEmail)
					return email;
			return null;
		}

	}

	/**
	 * It contains the processor part of the step
	 * 
	 * @author User
	 *
	 */
	public class EmailProcessorBatch implements ItemProcessor<Email, Email> {

		@Override
		public Email process(Email item) throws Exception {
			return item;
		}
	}

	/**
	 * It contains the writer part of the step
	 * 
	 * @author JParra
	 *
	 */
	public class EmailWriterBatch implements ItemWriter<Email> {

		@Autowired
		private EmailService emailService;

		@Override
		public void write(List<? extends Email> items) throws Exception {
			for (Email email : items)
				this.emailService.sendEmail(email);
		}

	}

	/**
	 * Define the job repository
	 * 
	 * @param factory
	 * @return
	 * @throws Exception
	 */
	@Bean
	public JobRepository jobRepository(MapJobRepositoryFactoryBean factory) throws Exception {
		return (JobRepository) factory.getObject();
	}

	/**
	 * Define the instance of launcher
	 * 
	 * @param jobRepository
	 * @return
	 */
	@Bean
	public SimpleJobLauncher jobLauncher(JobRepository jobRepository) {
		SimpleJobLauncher launcher = new SimpleJobLauncher();
		launcher.setJobRepository(jobRepository);
		return launcher;
	}

}
