package com.ericafenyo.carehub.contexts;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "set")
public class CreateNoteContext {
    private String title;
    private String content;
    private String teamId;
    private String userId;
}
