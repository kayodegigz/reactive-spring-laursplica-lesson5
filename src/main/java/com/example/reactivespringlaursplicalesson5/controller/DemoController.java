package com.example.reactivespringlaursplicalesson5.controller;

import com.example.reactivespringlaursplicalesson5.publisher.DemoPublisher;
import com.example.reactivespringlaursplicalesson5.subscriber.DemoSubscriber;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

/**
 * Created by Kayode.Ogunrinde on 3/16/2023.
 */

@RestController
public class DemoController {

    DemoPublisher f1 = new DemoPublisher(List.of(1, 2, 3, 4, 5));
    Flux<String> f2 = Flux.create(fluxSink -> {
        for (int i = 0; i < 10; i++) {
            fluxSink.next(UUID.randomUUID().toString());
        }
        // after all events have been completed
        fluxSink.complete();
    });

    @GetMapping("/")
    public void demo() {
        f1.subscribe(new DemoSubscriber());
        f2.subscribe(new Subscriber<String>() {
            Subscription subscription;
            int i = 0;

            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                this.subscription.request(1);
            }

            /**
             * Little implementation to handle backpressure
             * If the request is on its second one, ie if the tracker is
             * a multiple of 2, request two more
             * @param s
             */
            @Override
            public void onNext(String s) {
                System.out.println(s);
                i++;
                if (i % 2 == 0) {
                    this.subscription.request(2);
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
