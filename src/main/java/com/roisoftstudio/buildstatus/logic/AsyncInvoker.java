package com.roisoftstudio.buildstatus.logic;

import com.roisoftstudio.buildstatus.presentation.api.dto.Versions;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncInvoker {

  private ExecutorService service = Executors.newFixedThreadPool(100);

  public List<Versions> invokeAll(List<Callable<Versions>> versionCallable) {
    try {
      return service.invokeAll(versionCallable).stream()
          .map(this::getFuture)
          .collect(Collectors.toList());
    } catch (InterruptedException e) {
      throw logAndRethrow(e);
    }
  }

  private RuntimeException logAndRethrow(Exception e) {
    log.error("Error completing the call: {}", e.getLocalizedMessage(), e);
    return new RuntimeException("Error getting the future: ", e);
  }

  private Versions getFuture(Future<Versions> versionsFuture) {
    try {
      return versionsFuture.get();
    } catch (InterruptedException | ExecutionException e) {
      throw logAndRethrow(e);
    }
  }
}
