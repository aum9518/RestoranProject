package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoCheque.AverageSumResponse;
import peaksoft.dto.dtoCheque.ChequeRequest;
import peaksoft.dto.dtoCheque.ChequeResponse;
import peaksoft.dto.dtoCheque.PaginationChequeResponse;

import java.time.LocalDate;

public interface ChequeService {
    PaginationChequeResponse getAllCheques();
    SimpleResponse saveCheque(Long userId,Long menuItemId);
    SimpleResponse updateCheque(Long id, ChequeRequest chequeRequest);
    ChequeResponse getChequeById(Long id);
    SimpleResponse deleteCheque(Long id);
    AverageSumResponse getAverageSum(LocalDate date);
    AverageSumResponse getAverageSumOfWaiter(Long waiterId, LocalDate dateTime);

}
