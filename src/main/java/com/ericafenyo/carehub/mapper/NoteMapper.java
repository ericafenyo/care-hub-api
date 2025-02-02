package com.ericafenyo.carehub.mapper;

import com.ericafenyo.carehub.entities.NoteEntity;
import com.ericafenyo.carehub.model.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

/**
 * Component that maps {@link NoteEntity} to {@link Note}.
 */
@Component
@RequiredArgsConstructor
public class NoteMapper implements Function<NoteEntity, Note> {
    private final UserMapper userMapper;
    private final TeamMapper teamMapper;

    /**
     * Applies this function to the given argument.
     *
     * @param entity the {@link NoteEntity} to be converted
     * @return the converted {@link Note}
     */
    @Override
    public Note apply(NoteEntity entity) {
        var note = new Note()
            .setId(entity.getId())
            .setTitle(entity.getTitle())
            .setContent(entity.getContent())
            .setCreatedAt(entity.getCreatedAt())
            .setUpdatedAt(entity.getUpdatedAt());

        Optional.ofNullable(entity.getAuthor())
            .ifPresent(author -> note.setAuthor(userMapper.apply(author)));

        Optional.ofNullable(entity.getTeam())
            .ifPresent(team -> note.setTeam(teamMapper.apply(team)));

        return note;
    }
}
