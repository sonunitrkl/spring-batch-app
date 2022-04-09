package com.ninjas.world.springbatchapp.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sonu Kumar
 */
@Component
public class FirstItemWriter implements ItemWriter<Long> {
    @Override
    public void write(List<? extends Long> list) throws Exception {
        System.out.println("Inside the Item Writer");
        list.stream().forEach(System.out::println);
    }

}
