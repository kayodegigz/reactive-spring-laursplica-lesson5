package com.example.reactivespringlaursplicalesson5.publisher;

import com.example.reactivespringlaursplicalesson5.subscription.DemoSubscription;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

/**
 * Created by Kayode.Ogunrinde on 3/16/2023.
 */


/**
 * An implementation of a custom publisher that takes in a list as argument
 */
public class DemoPublisher implements Publisher<Integer> {
    private final List<Integer> list;

    public DemoPublisher(List<Integer> list) {
        this.list = list;
    }


    @Override
    public void subscribe(Subscriber<? super Integer> subscriber) {
        // the publisher will now give the subscription to the subscriber
        Subscription subscription = new Subscription() {
            private int lastRequestedElementIndex = -1;

            @Override
            public void request(long l) {
                lastRequestedElementIndex ++;
                if (lastRequestedElementIndex < list.size()){
                    subscriber.onNext(list.get(lastRequestedElementIndex));
                }
                else {
                    subscriber.onComplete();
                }
            }

            @Override
            public void cancel() {

            }
        };
        subscriber.onSubscribe(subscription);
    }
}
