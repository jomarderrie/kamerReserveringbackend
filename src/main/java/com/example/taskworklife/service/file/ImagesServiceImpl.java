package com.example.taskworklife.service.file;

import com.example.taskworklife.config.ReserveringConfiguration;
import com.example.taskworklife.exception.global.ChangeOnlyOwnUserException;
import com.example.taskworklife.exception.global.IoException;
import com.example.taskworklife.exception.images.ImageNotFoundException;
import com.example.taskworklife.exception.images.ImageTypeNotAllowedException;
import com.example.taskworklife.exception.images.ImagesExceededLimit;
import com.example.taskworklife.exception.images.ImagesNotFoundException;
import com.example.taskworklife.exception.kamer.KamerNaamIsLeegException;
import com.example.taskworklife.exception.kamer.KamerNaamNotFoundException;
import com.example.taskworklife.exception.kamer.KamerNotFoundException;
import com.example.taskworklife.exception.user.EmailNotFoundException;
import com.example.taskworklife.fileservice.FileService;
import com.example.taskworklife.models.Kamer;
import com.example.taskworklife.models.attachment.KamerFileAttachment;
import com.example.taskworklife.models.attachment.ProfileFileAttachment;
import com.example.taskworklife.models.user.User;
import com.example.taskworklife.repo.FileAttachmentRepo;
import com.example.taskworklife.repo.KamerRepo;
import com.example.taskworklife.repo.UserRepo;
import com.example.taskworklife.service.kamer.KamerService;
import com.example.taskworklife.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ImagesServiceImpl implements ImagesService {

    FileService fileService;
    KamerService kamerService;
    ReserveringConfiguration reserveringConfiguration;
    FileAttachmentRepo fileAttachmentRepository;
    KamerRepo kamerRepo;
    UserRepo userRepo;
    UserService userService;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());


    public ImagesServiceImpl(FileService fileService, KamerService kamerService, ReserveringConfiguration reserveringConfiguration, FileAttachmentRepo fileAttachmentRepository, KamerRepo kamerRepo, UserRepo userRepo, UserService userService) {
        this.fileService = fileService;
        this.kamerService = kamerService;
        this.reserveringConfiguration = reserveringConfiguration;
        this.fileAttachmentRepository = fileAttachmentRepository;
        this.kamerRepo = kamerRepo;
        this.userRepo = userRepo;
        this.userService = userService;
    }

    public Kamer deleteAllKamerImages(Kamer kamer) throws IOException {
        List<KamerFileAttachment> attachments = kamer.getAttachments();
        if (attachments.size() > 0) {
            kamer.setAttachments(new ArrayList<>());
        }
        Files.deleteIfExists(Paths.get(reserveringConfiguration.getKamerFolder() + "/" + kamer.getNaam()));
        LOGGER.info("alle kamer attachments zijn weggehaald voor kamer " + kamer.getNaam());
        return kamer;
    }

    public void deleteKamerImage(String naam) {
        try {
            FileUtils.deleteQuietly(new File(reserveringConfiguration.getKamerFolder() + "/" + naam));
            Files.deleteIfExists(Paths.get(reserveringConfiguration.getKamerFolder() + "/" + naam));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        saveKamerImage()
    }


    @Override
    public boolean saveKamerImage(String kamerNaam, MultipartFile[] files) throws KamerNotFoundException, ImageTypeNotAllowedException, ImagesExceededLimit, ImagesNotFoundException, IOException, KamerNaamIsLeegException, KamerNaamNotFoundException, IoException {
        //check if files are not null
        if (files != null) {
            //check if length is not 0
            if (files.length != 0) {
                //check if naam is not empty
                if (StringUtils.isNotBlank(kamerNaam)) {
                    if (files.length > 6) {
                        throw new ImagesExceededLimit("Niet meer dan 6 images");
                    }
                    //check if naam exits in db
                    Kamer kamerByNaam = kamerService.getKamerByNaam(kamerNaam);
                    // detect if image
                    for (MultipartFile file : files) {
                        if (!fileService.detectIfImage(Objects.requireNonNull(file.getContentType()))) {
                            throw new ImageTypeNotAllowedException("Image type is niet toegestaan alleen png, jpg, jpeg");
                        }
                    }

                    Date date;
                    //delete all kamerimages
                    File kamerDirectory = new File(reserveringConfiguration.getKamerFolder() + "/" + kamerByNaam.getNaam());


                    FileUtils.deleteDirectory(kamerDirectory);
                    List<KamerFileAttachment> attachments = kamerByNaam.getAttachments();
                    kamerByNaam.setAttachments(new ArrayList<>());
                    attachments.forEach(p ->{
                        p.setKamer(null);
                        fileAttachmentRepository.deleteById(p.getId());
                    });
                    System.out.println(kamerByNaam);
                    fileService.maakDirectory(kamerDirectory);
                    for (MultipartFile file : files) {
                        date = new Date();
                        KamerFileAttachment fileAttachment = new KamerFileAttachment();
                        fileAttachment.setDate(date);

                        try {
                            byte[] fileAsByte = file.getBytes();
                            try {
                                File target = new File(reserveringConfiguration.getKamerFolder() + "/" +
                                        kamerByNaam.getNaam() + "/" + file.getOriginalFilename());
                                FileUtils.writeByteArrayToFile(target, fileAsByte);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }

                            fileAttachment.setFileType(file.getContentType());
                            fileAttachment.setName(file.getOriginalFilename());
                            kamerByNaam.addFileAttachment(fileAttachment);
                            LOGGER.info("Saved image with name " + file.getOriginalFilename() + " for room " + kamerNaam);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new IoException(e.getMessage());
                        }
                    }

                    kamerRepo.save(kamerByNaam);
                    return true;
                } else {
                    throw new KamerNaamIsLeegException("Kamer niet gevonden met de naam " + kamerNaam);
                }
            } else {
                throw new ImagesNotFoundException("Geen images gevonden");
            }
        } else {
            throw new ImagesNotFoundException("Geen images gevonden");
        }
    }

    @Override
    public boolean saveProfileImageUser(String principalEmail, String emailPath, MultipartFile profileImage) throws ImageNotFoundException, EmailNotFoundException, ImageTypeNotAllowedException, IoException, ChangeOnlyOwnUserException {
        if (profileImage != null) {
            if (!profileImage.isEmpty()) {
                if (StringUtils.isNotBlank(principalEmail) || StringUtils.isNotBlank(emailPath)) {
                    if (emailPath.equals(principalEmail)) {

                        if (!fileService.detectIfImage(Objects.requireNonNull(profileImage.getContentType()))) {
                            throw new ImageTypeNotAllowedException("Image type is niet toegestaan alleen png, jpg, jpeg");
                        }
                        Date date = new Date();

                        User userByEmail = userService.findUserByEmail(principalEmail);
                        if (userByEmail != null) {
                            if (userByEmail.getProfileFileAttachment() != null) {
                                Long attachmentId = userByEmail.getProfileFileAttachment().getId();
                                if (attachmentId != null) {
                                    fileAttachmentRepository.deleteById(attachmentId);
                                }
                            }
                        }


                        File profileDirectory = new File(reserveringConfiguration.getProfileImagesPath() + "/" + principalEmail);
                        FileUtils.deleteQuietly(profileDirectory);
                        fileService.maakDirectory(profileDirectory);
                        ProfileFileAttachment fileAttachment = new ProfileFileAttachment();
                        fileAttachment.setDate(date);

                        try {
                            byte[] fileAsByte = profileImage.getBytes();
                            File target = new File(reserveringConfiguration.getProfileImagesPath() + "/" + principalEmail + "/" + profileImage.getOriginalFilename());
                            FileUtils.writeByteArrayToFile(target, fileAsByte);
                            fileAttachment.setName(profileImage.getOriginalFilename());
                            fileAttachment.setFileType(profileImage.getContentType());
                            fileAttachment.setUser(userByEmail);
                            if (userByEmail != null) {
                                userByEmail.setProfileFileAttachment(fileAttachment);
                                userRepo.save(userByEmail);
                            }
                            LOGGER.info("Profile image saved for principalEmail " + principalEmail);
                            return true;
                        } catch (IOException e) {
                            throw new IoException(e.getMessage());
                        }
                    } else {
                        throw new ChangeOnlyOwnUserException("Kan alleen je eigen account veranderen");
                    }
                } else {
                    throw new EmailNotFoundException("geen foto gevonden");
                }
            } else {
                throw new ImageNotFoundException("Email niet gevonden");
            }
        } else {
            throw new ImageNotFoundException("geen profiel image gevonden");
        }
    }

    @Override
    public boolean deleteProfileImageUser(String principalEmail, String emailPath) throws ChangeOnlyOwnUserException, EmailNotFoundException, ImageNotFoundException {
        if (StringUtils.isNotBlank(principalEmail) || StringUtils.isNotBlank(emailPath)) {
            if (emailPath.equals(principalEmail)) {
                User userByEmail = userService.findUserByEmail(principalEmail);
                if (userByEmail.getProfileFileAttachment() != null) {
                    Long attachmentId = userByEmail.getProfileFileAttachment().getId();
                    if (attachmentId != null) {

                        userByEmail.setProfileFileAttachment(null);
                        userRepo.save(userByEmail);
                        File profileDirectory = new File(reserveringConfiguration.getProfileImagesPath() + "/" + principalEmail);
                        FileUtils.deleteQuietly(profileDirectory);

                        fileAttachmentRepository.deleteById(attachmentId);
                        LOGGER.info("Profile image verwijderd " + principalEmail);
                        return true;
                    } else {
                        throw new ImageNotFoundException("geen profiel image gevonden");
                    }
                } else {
                    throw new ImageNotFoundException("geen profiel image gevonden");
                }
            } else {
                throw new ChangeOnlyOwnUserException("Kan alleen je eigen account veranderen");
            }
        } else {
            throw new EmailNotFoundException("Email niet gevonden");
        }
    }


}
