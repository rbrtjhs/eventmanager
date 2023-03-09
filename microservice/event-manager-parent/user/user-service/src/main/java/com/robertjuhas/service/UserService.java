package com.robertjuhas.service;

import com.robertjuhas.dto.request.SubscribeRequestDTO;
import com.robertjuhas.dto.response.UserResponseDTO;
import com.robertjuhas.dto.request.CreateUserRequestDTO;
import com.robertjuhas.entity.Subscription;
import com.robertjuhas.entity.SubscriptionID;
import com.robertjuhas.entity.User;
import com.robertjuhas.messaging.dto.MessagingEventEventCreated;
import com.robertjuhas.messaging.dto.MessagingEventEventUpdated;
import com.robertjuhas.messaging.dto.MessagingEventNotifyUsersEventCreated;
import com.robertjuhas.messaging.dto.MessagingEventNotifyUsersEventUpdated;
import com.robertjuhas.messenger.KafkaMessenger;
import com.robertjuhas.repository.SubscriptionRepository;
import com.robertjuhas.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private SubscriptionRepository subscriptionRepository;
    private KafkaMessenger kafkaMessenger;
    private PasswordEncoder passwordEncoder;

    @Transactional("transactionManager")
    public void save(CreateUserRequestDTO userRequest) {
        var user = new User();
        user.setUsername(userRequest.username());
        String encodedPassword = passwordEncoder.encode(userRequest.password());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public void gatherUserInfo(MessagingEventEventCreated eventCreated) {
        var subscriptions = subscriptionRepository.findBySubscribedTo(eventCreated.userID());
        if (subscriptions != null && !subscriptions.isEmpty()) {
            var notifyUsers = subscriptions.stream().map(x -> x.getSubscriber().getUsername()).toList();
            var messagingEvent = new MessagingEventNotifyUsersEventCreated(eventCreated.time(), eventCreated.capacity(), eventCreated.place(), eventCreated.title(), notifyUsers, eventCreated.eventID(), subscriptions.get(0).getSubscribedTo().getUsername());
            kafkaMessenger.eventCreatedNotifyUsers(messagingEvent);
        }
    }

    public void gatherUserInfo(MessagingEventEventUpdated eventUpdated) {
        var subscriptions = userRepository.findAllById(eventUpdated.users());
        if (!subscriptions.isEmpty()) {
            var notifyUsers = subscriptions.stream().map(x -> x.getUsername()).toList();
            var messagingEvent = new MessagingEventNotifyUsersEventUpdated(eventUpdated.title(), notifyUsers, eventUpdated.eventID());
            kafkaMessenger.eventUpdatedNotifyUsers(messagingEvent);
        }
    }

    @Transactional("transactionManager")
    public void subscribe(SubscribeRequestDTO subscribe) {
        var subscription = new Subscription();
        var subscriptionID = new SubscriptionID();
        subscriptionID.setSubscribedTo(subscribe.subscribedTo());
        subscriptionID.setSubscriber(subscribe.subscriber());
        subscription.setId(subscriptionID);
        subscriptionRepository.save(subscription);
    }

    public UserResponseDTO getUser(long id) {
        var user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NoSuchElementException("User with id " + id + " does not exists.");
        }
        return new UserResponseDTO(user.get().getId(), user.get().getUsername());
    }
}
