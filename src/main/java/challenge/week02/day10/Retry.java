package challenge.week02.day10;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {
	
	int counter = 0, maxRetry = 1;

	@Override
	public boolean retry(ITestResult result) {
		if(counter < maxRetry) {
			counter++;
			return true;
		}
		return false;
	}

}