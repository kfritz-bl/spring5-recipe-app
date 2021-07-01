package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jt on 7/3/17.
 */
@Slf4j
@Controller
public class ImageController {

    private final ImageService imageSvc;
    private final RecipeService recipeSvc;

    public ImageController(ImageService imageSvc, RecipeService recipeSvc) {
        this.imageSvc = imageSvc;
        this.recipeSvc = recipeSvc;
    }

    @GetMapping("recipe/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model) {
        log.debug("Getting the Upload Form page");
        model.addAttribute("recipe", recipeSvc.findCommandById(Long.valueOf(id)));

        return "recipe/imageuploadform";
    }

    @PostMapping("recipe/{id}/image")
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file) {
        log.debug("Saving Image");
        imageSvc.saveImageFile(Long.valueOf(id), file);

        return String.format("redirect:/recipe/%s/show", id);
    }

    @GetMapping("recipe/{id}/recipeimage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        log.debug("Showing Stored Image");
        RecipeCommand recipeCmd = recipeSvc.findCommandById(Long.valueOf(id));

        if (recipeCmd.getImage() != null) {
            byte[] byteArray = new byte[recipeCmd.getImage().length];
            int i = 0;

            for (Byte wrappedByte : recipeCmd.getImage()) {
                byteArray[i++] = wrappedByte; //auto unboxing
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}
