package ar.com.plug.examen.domain.service.impl;

import ar.com.plug.examen.datasource.model.Purchase;
import ar.com.plug.examen.datasource.repo.PurchaseRepo;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ProcessPurchaseServiceImpl extends AbstractIdModelService<PurchaseRepo, Purchase> {

    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


    public ProcessPurchaseServiceImpl(PurchaseRepo repo) {
        super(repo);
    }

    public void approve(Long id, Boolean approve) {
        Purchase purchase = repo.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Purchase with Id: %s not found", id)));
        purchase.setApproved(approve);
        repo.save(purchase);
    }

    public List<Purchase> findAllByCreationDateTimeBetween(String startDate, String endDate) {
        try {
            return repo.findAllByCreationDateTimeBetween(simpleDateFormat.parse(startDate), simpleDateFormat.parse(endDate));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date", e);
        }
    }

    @Override
    Class<Purchase> getDomainClass() {
        return Purchase.class;
    }


}