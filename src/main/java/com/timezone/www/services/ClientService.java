package com.timezone.www.services;

import com.timezone.www.model.Client;

import java.util.List;
import java.util.TimeZone;

public interface ClientService extends CrudService<Client, Long> {

    Client findByCompanyName(String companyName);

    List<Client> findAllByCompanyNameLike(String companyName);

    Client findByTimeZone(TimeZone timeZone);

    List<Client> findAllByUserId(Long userId);
}
