package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.FollowArtistRepository;
import repository.FollowEventRepository;

@Service
public class FollowEventServiceImpl {

    private final FollowEventRepository followEventRepository;

    @Autowired
    public FollowEventServiceImpl(FollowEventRepository followEventRepository) {
        this.followEventRepository = followEventRepository;
    }


}
