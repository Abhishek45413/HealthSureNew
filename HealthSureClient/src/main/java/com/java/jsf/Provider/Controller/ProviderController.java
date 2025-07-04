package com.java.jsf.Provider.Controller;

import java.util.List;

import com.java.jsf.Provider.dao.ProviderDao;
import com.java.jsf.Provider.daoImpl.ProviderDaoImpl;
import com.java.jsf.Provider.model.LoginStatus;
import com.java.jsf.Provider.model.Provider;

public class ProviderController {
    private Provider provider = new Provider();
    private List<Provider> providers;

    private ProviderDao providerDao = new ProviderDaoImpl();

    // Register a new provider
    public String register() throws Exception {
        System.out.println("Registering provider...");
        if (provider == null) {
            System.out.println("Provider object is null!");
        } else {
            System.out.println("Provider before setting status: " + provider);
            provider.setStatus(LoginStatus.PENDING);
        }

        if (providerDao == null) {
            System.out.println("ProviderDao is null!");
        } else {
            System.out.println("Calling providerDao.addProvider()");
            providerDao.addProvider(provider);
        }

        return "Success";
    }

    // Fetch specific provider by ID
    public Provider getProviderById(String providerId) throws Exception {
        System.out.println("Fetching provider by ID: " + providerId);
        if (providerDao == null) {
            System.out.println("ProviderDao is null!");
            return null;
        }
        Provider result = providerDao.searchByProviderId(providerId);
        System.out.println("Fetched provider: " + result);
        return result;
    }

    // Update provider (admin approval/rejection etc.)
    public String updateStatus(String providerId, LoginStatus status) throws Exception {
        System.out.println("Updating status for provider ID: " + providerId + " to " + status);
        if (providerDao == null) {
            System.out.println("ProviderDao is null!");
            return "error";
        }

        Provider p = providerDao.searchByProviderId(providerId);
        if (p == null) {
            System.out.println("Provider not found for ID: " + providerId);
        } else {
            System.out.println("Provider found: " + p);
            p.setStatus(status);
            providerDao.updateProvider(p);
        }

        return "dashboard";
    }

    // Delete provider
    public String delete(String providerId) throws Exception {
        System.out.println("Deleting provider with ID: " + providerId);
        if (providerDao == null) {
            System.out.println("ProviderDao is null!");
            return "error";
        }
        providerDao.deleteProvider(providerId);
        return "provider_list";
    }

    public Provider getProvider() {
        System.out.println("Getting provider: " + provider);
        return provider;
    }

    public void setProvider(Provider provider) {
        System.out.println("Setting provider: " + provider);
        this.provider = provider;
    }
}