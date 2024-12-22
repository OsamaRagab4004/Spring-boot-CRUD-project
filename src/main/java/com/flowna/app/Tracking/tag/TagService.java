package com.flowna.app.Tracking.tag;


import com.flowna.app.user.User;
import com.flowna.app.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class TagService {
    final
    TagRepository tagRepository;
    final UserRepository userRepository;

    public TagService(TagRepository tagRepository, UserRepository userRepository) {
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
    }


    public Tag create(TagRequest tagRequest, int userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );
        Set<Tag> tags = user.getTags();
        // Check if the tag already exists
        tags.stream().filter( tag -> tag.getName().equals(tagRequest.getTag())).findFirst().ifPresent(
                tag -> {
                    throw new IllegalArgumentException("Tag already exists");
                }
        );
        Tag tag = Tag.builder()
                .name(tagRequest.getTag())
                .user(user)
                .build();
        tagRepository.save(tag);
        return tag;

    }

    public Set<Tag> getTags(int userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );
        return user.getTags();
    }

   public Tag update(int userId, TagRequest request) {
    User user = userRepository.findById(userId).orElseThrow(
            () -> new IllegalArgumentException("User not found")
    );
    Set<Tag> tags = user.getTags();
    // check if tag is unique or not, excluding the tag being updated
    Optional<Tag> tag = tags.stream()
            .filter(t -> t.getName().equals(request.getTag()))
            .findFirst();
    if(tag.isPresent()) throw new IllegalStateException("Tag is already present");
    Tag updatedTag = tagRepository.findById(request.getId()).orElseThrow(
            () -> new IllegalArgumentException("Tag not found")
    );
    updatedTag.setName(request.getTag());
    tagRepository.save(updatedTag);
    return updatedTag;
}



}
