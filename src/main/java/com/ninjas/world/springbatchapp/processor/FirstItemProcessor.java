package com.ninjas.world.springbatchapp.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * @author Sonu Kumar
 */
@Component
public class FirstItemProcessor implements ItemProcessor<Integer,Long> {
    @Override
    public Long process(Integer item) throws Exception {
        System.out.println("inside the itemProcessor");
        return Long.valueOf(item + 20);
    }
}
