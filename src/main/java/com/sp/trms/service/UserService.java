package com.sp.trms.service;

import com.sp.trms.dao.UserDao;
import com.sp.trms.domain.Customer;
import com.sp.trms.domain.RentalAgreement;
import com.sp.trms.domain.User;
import com.sp.trms.exception.RentalDataNotFoundException;
import org.springframework.stereotype.Service;

/**
 * User Service to serve functions like getting users and assigning agreements to customers etc.
 */
@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Method to get user object for given user id.
     * @param userId - User Id
     * @return - Return user object.
     */
    public User getUser(String userId) {
        return this.userDao.getUser(userId);
    }

    /**
     * Assing agreement to a given customer id.
     * @param customerId - Customer Id
     * @param rentalAgreement - Rental Agreement
     * @throws RentalDataNotFoundException - Checked exception to throw for data issues.
     */
    public void addAgreementToCustomer(String customerId, RentalAgreement rentalAgreement)
            throws RentalDataNotFoundException {
        Customer customer = (Customer) userDao.getUser(customerId);
        if (customer == null) {
            throw new RentalDataNotFoundException("Unable to find customer with id: " + customerId);
        }
        customer.getAgreements().add(rentalAgreement);
    }
}
