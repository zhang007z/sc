package com.github._1element.sc.service; //NOSONAR

import com.github._1element.sc.domain.Camera;
import com.github._1element.sc.domain.SurveillanceImage;
import com.github._1element.sc.dto.ImagesCameraSummaryResult;
import com.github._1element.sc.repository.CameraRepository;
import com.github._1element.sc.repository.SurveillanceImageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Main surveillance service class.
 */
@Service
public class SurveillanceService {

  private final SurveillanceImageRepository imageRepository;

  private final CameraRepository cameraRepository;

  @Autowired
  public SurveillanceService(final SurveillanceImageRepository imageRepository,
                             final CameraRepository cameraRepository) {
    this.imageRepository = imageRepository;
    this.cameraRepository = cameraRepository;
  }

  /**
   * Returns page of surveillance images.
   *
   * @param camera      optional camera identifier
   * @param date        optional date
   * @param isArchive   archive flag
   * @param pageable    page request
   * @return the page of surveillance images
   */
  public Page<SurveillanceImage> getImagesPage(final Optional<String> camera,
                                               final Optional<LocalDate> date,
                                               final boolean isArchive,
                                               final Pageable pageable) {
    LocalDateTime startOfDay = null;
    LocalDateTime endOfDay = null;

    if (date.isPresent()) {
      startOfDay = date.get().atStartOfDay();
      endOfDay = LocalDateTime.of(date.get(), LocalTime.MAX);
    }

    if (camera.isPresent() && StringUtils.isNotBlank(camera.get())) {
      if (date.isPresent()) {
        return imageRepository.findAllForDateRangeAndCameraId(startOfDay, endOfDay, camera.get(), isArchive,
            pageable);
      }

      return imageRepository.findAllByCameraIdAndArchived(camera.get(), isArchive, pageable);
    }

    if (date.isPresent()) {
      return imageRepository.findAllForDateRange(startOfDay, endOfDay, isArchive, pageable);
    }

    return imageRepository.findAllByArchived(isArchive, pageable);
  }

  /**
   * Returns images summary for each camera.
   *
   * @return the images camera summary result
   */
  public List<ImagesCameraSummaryResult> getImagesCameraSummary() {
    final List<ImagesCameraSummaryResult> result = new ArrayList<>();
    final PageRequest limitPageRequest = new PageRequest(0, 1);

    final List<Camera> cameras = cameraRepository.findAll();

    for (final Camera camera : cameras) {
      final Long count = imageRepository.countImagesForCamera(camera.getId());
      LocalDateTime mostRecentDate = null;
      if (count > 0) {
        final List<LocalDateTime> mostRecentDateList = imageRepository.getMostRecentImageDateForCamera(
            camera.getId(), limitPageRequest);
        mostRecentDate = mostRecentDateList.stream().findFirst().orElse(null);
      }
      result.add(new ImagesCameraSummaryResult(camera, count, mostRecentDate));
    }

    return result;
  }

}
