package com.project.ms_order.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrdersAMQPConfig {
    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connection,
                                         Jackson2JsonMessageConverter jackson2JsonMessageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connection);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitTemplate;

    }
    @Bean
    public Queue queueDetails() {
        return QueueBuilder
                .nonDurable("order.details-requests")
                .build();
    }
    @Bean
    public FanoutExchange fanoutExchange() {
        return ExchangeBuilder
                .fanoutExchange("order.ex")
                .build();
    }
    @Bean
    public Binding bindPaymentRequests(FanoutExchange fanoutExchange) {
        return BindingBuilder
                .bind(queueDetails())
                .to(fanoutExchange());
    }
    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory conn) {
        return new RabbitAdmin(conn);
    }
    @Bean
    public ApplicationListener<ApplicationReadyEvent> adminInit(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }


}
