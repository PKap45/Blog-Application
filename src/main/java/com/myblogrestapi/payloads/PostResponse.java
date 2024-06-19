package com.myblogrestapi.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private List<PostDto> postDto;
    private int pageNo;
    private int pageSize;
    private int totalElements;
    private int totalPages;

    private boolean last;

}
