package hr.foi.raspberry.listener.model.beacon;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BeaconRecordPrecisionCalculator {

    private static final Logger logger = LoggerFactory.getLogger(BeaconRecordPrecisionCalculator.class);

    public double calculatePreciseDistance(List<Double> distances) {
        Mean mean = new Mean();
        Percentile p = new Percentile();

        double[] values = distances.stream().mapToDouble(e -> e).toArray();
        logger.info("Mean before trim: {}", mean.evaluate(values, 0, values.length));

        double lower = p.evaluate(values, 0, values.length, 25);
        double upper = p.evaluate(values, 0, values.length, 75);
        double diff = upper - lower;
        double variable = diff * 1.5;

        logger.info("q25:{}, q75:{} diff:{} diff*1.5:{}", lower, upper, diff, variable);

        final double lowerRange = lower - variable;
        final double upperRange = upper + variable;

        logger.info("deleting values: {}< and >{}", lower, upperRange);

        distances = distances.stream().filter(e -> e >= lowerRange && e <= upperRange).collect(Collectors.toList());
        values = distances.stream().mapToDouble(e -> e).toArray();

        double result = mean.evaluate(values, 0, values.length);

        logger.info("Mean after trim: {}", result);

        return result;
    }

}
