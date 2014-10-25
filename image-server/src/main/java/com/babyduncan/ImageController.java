package com.babyduncan;

import com.google.common.collect.Lists;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * User: guohaozhao (guohaozhao116008@sohu-inc.com)
 * Date: 10/12/14 15:12
 */
@Controller
public class ImageController {

    private static final Logger logger = Logger.getLogger(ImageController.class);
    private static final String IMAGE_PREFIX = "image";
    private static final String IMAGE_DIR = "/opt/image";

    @RequestMapping(value = "/imageserver/index", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("code", 0);
        return modelAndView;
    }

    /**
     * 文件上传
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/imageserver/uploadFiles", method = RequestMethod.POST)
    public ModelAndView uploadMultiPartImages(@RequestParam MultipartFile[] myFiles, HttpServletRequest request, HttpServletResponse response) throws FileUploadException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("image");
        modelAndView.addObject("code", 0);
        List<String> fileNames = Lists.newArrayList();
        for (MultipartFile myFile : myFiles) {
            if (myFile.isEmpty()) {
                continue;
            }
            String fileName = processUploadedFile(myFile);
            fileNames.add(fileName);
        }
        modelAndView.addObject("data", fileNames);
        return modelAndView;
    }

    @RequestMapping(value = "/imageserver/uploadFile", method = RequestMethod.POST)
    public ModelAndView uploadMultiPartImage(@RequestParam MultipartFile myFile, HttpServletRequest request, HttpServletResponse response) throws FileUploadException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("image");
        modelAndView.addObject("code", 0);
        if (!myFile.isEmpty()) {
            String fileName = processUploadedFile(myFile);
            modelAndView.addObject("data", fileName);
        }
        return modelAndView;
    }

    private String processUploadedFile(MultipartFile myFile) {
        String fileName__ = new StringBuilder().append(IMAGE_PREFIX).append(System.currentTimeMillis()).append(myFile.getOriginalFilename()).toString();
        File uploadedFile = new File(new StringBuilder().append(IMAGE_DIR).append("/o/").append(fileName__).toString());
        String uploadedFile175 = new StringBuilder().append(IMAGE_DIR).append("/l/").append(fileName__).toString();
        String uploadedFile95 = new StringBuilder().append(IMAGE_DIR).append("/m/").append(fileName__).toString();
        String uploadedFile55 = new StringBuilder().append(IMAGE_DIR).append("/s/").append(fileName__).toString();
        try {
            FileUtils.copyInputStreamToFile(myFile.getInputStream(), uploadedFile);
            EasyImage easyImage = new EasyImage(uploadedFile);
            easyImage.resize(175, 175);
            easyImage.saveAs(uploadedFile175);
            easyImage = new EasyImage(uploadedFile);
            easyImage.resize(95, 95);
            easyImage.saveAs(uploadedFile95);
            easyImage = new EasyImage(uploadedFile);
            easyImage.resize(55, 55);
            easyImage.saveAs(uploadedFile55);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return fileName__;
    }


    @RequestMapping(value = "/imageserver/uploadStream", method = RequestMethod.POST)
    public ModelAndView uploadImageStream(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }


}
