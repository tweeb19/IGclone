package com.example.FEMS.services;

import com.example.FEMS.models.Comment;
import com.example.FEMS.models.PageOfItems;
import com.example.FEMS.models.Post;
import com.example.FEMS.models.PostFrontEnd;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class TestPostFrontEndServiceImpl {

    @InjectMocks
    PostFrontEndServiceImpl pfe;

    @Mock
    PostServiceImpl psi;

    @Mock
    CommentServiceImpl csi;

    @Mock
    UserServiceImpl usi;

    PageOfItems<Post> post;
    PageOfItems<PostFrontEnd> frontendpost;
    List<PageOfItems<Comment>> pageofcomments;
    int pageNumber;
    int pageSize;

    @BeforeEach
    public void init(){
        Post testPost = new Post(1, 1, "imgsource", "imgdesc", LocalDate.parse("2022-04-28"));
        Post testPost2 = new Post(2, 1, "imgsource", "imgdesc", LocalDate.parse("2022-04-28"));
        Post testPost3 = new Post(3, 1, "imgsource", "imgdesc", LocalDate.parse("2022-04-28"));

        List<Post> testposts = new ArrayList<Post>(){{add(testPost); add(testPost2); add(testPost3);}};
        post = new PageOfItems<>(testposts, false, testposts.size());

        Comment testcomment = new Comment(1, 1, "testuser", "test comment content", LocalDate.parse("2022-04-28"));
        Comment testcomment2 = new Comment(2, 1, "testuser", "test comment content", LocalDate.parse("2022-04-28"));
        Comment testcomment3 = new Comment(3, 1, "testuser", "test comment content", LocalDate.parse("2022-04-28"));

        List<Comment> testcomments = new ArrayList<Comment>(){{add(testcomment); add(testcomment2); add(testcomment3);}};

        Comment testcomment4 = new Comment(4, 2, "testuser", "test comment content", LocalDate.parse("2022-04-28"));
        Comment testcomment5 = new Comment(5, 2, "testuser", "test comment content", LocalDate.parse("2022-04-28"));
        Comment testcomment6 = new Comment(6, 2, "testuser", "test comment content", LocalDate.parse("2022-04-28"));

        List<Comment> testcomments2 = new ArrayList<Comment>(){{add(testcomment4); add(testcomment5); add(testcomment6);}};

        Comment testcomment7 = new Comment(7, 3, "testuser", "test comment content", LocalDate.parse("2022-04-28"));
        Comment testcomment8 = new Comment(8, 3, "testuser", "test comment content", LocalDate.parse("2022-04-28"));
        Comment testcomment9 = new Comment(9, 3, "testuser", "test comment content", LocalDate.parse("2022-04-28"));

        List<Comment> testcomments3 = new ArrayList<Comment>(){{add(testcomment7); add(testcomment8); add(testcomment9);}};
        pageofcomments = new ArrayList<>();
        pageofcomments.add(new PageOfItems<>(testcomments, false, testcomments.size()));
        pageofcomments.add(new PageOfItems<>(testcomments2, false, testcomments.size()));
        pageofcomments.add(new PageOfItems<>(testcomments3, false, testcomments.size()));

        PostFrontEnd postfrontend = new PostFrontEnd(testposts.get(0).getId(), usi.getUserById(testposts.get(0).getUserId()), testposts.get(0).getImg(), testposts.get(0).getDescription(), testposts.get(0).getCreatedOn(), pageofcomments.get(0));
        PostFrontEnd postfrontend2 = new PostFrontEnd(testposts.get(1).getId(), usi.getUserById(testposts.get(1).getUserId()), testposts.get(1).getImg(), testposts.get(1).getDescription(), testposts.get(1).getCreatedOn(), pageofcomments.get(1));
        PostFrontEnd postfrontend3 = new PostFrontEnd(testposts.get(2).getId(), usi.getUserById(testposts.get(2).getUserId()), testposts.get(2).getImg(), testposts.get(2).getDescription(), testposts.get(2).getCreatedOn(), pageofcomments.get(2));

        List<PostFrontEnd> postfrontendlist = new ArrayList<PostFrontEnd>(){{add(postfrontend); add(postfrontend2); add(postfrontend3);}};
        frontendpost = new PageOfItems<>(postfrontendlist, false, postfrontendlist.size());

        pageNumber = 1;
        pageSize = 5;
    }

    @Test
    void getAll(){
        Mockito.when(psi.getPost(pageNumber, pageSize)).thenReturn(post);
        for(Post postloop : post.getItems()) {
            Mockito.when(csi.getAllComments(postloop.getId(), 0, pageSize)).thenReturn(pageofcomments.get(postloop.getId()-1));
        }
        PageOfItems<PostFrontEnd> postfrontendgetall = pfe.getAll(pageNumber, pageSize);
        assertEquals(postfrontendgetall, frontendpost);
    }

    @Test
    void getPost_PageNumberNotNegative(){
        Mockito.when(psi.getPost(pageNumber, pageSize)).thenReturn(post);
        PageOfItems<PostFrontEnd> postfrontendgetall = pfe.getAll(-1, pageSize);
        assertEquals(postfrontendgetall, new PageOfItems<>(null, false, 0));
    }

    @Test
    void getPost_PageSizeNotNegative(){
        Mockito.when(psi.getPost(pageNumber, pageSize)).thenReturn(post);
        PageOfItems<PostFrontEnd> postfrontendgetall = pfe.getAll(pageNumber, -1);
        assertEquals(postfrontendgetall, new PageOfItems<>(null, false, 0));
    }
}
