/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.repository.jpa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA implementation of the {@link OwnerRepository} interface.
 *
 * @author Mike Keith
 * @author Rod Johnson
 * @author Sam Brannen
 * @author Michael Isvy
 * @since 22.4.2006
 */
@Repository
public class JpaOwnerRepositoryImpl implements OwnerRepository {

    @PersistenceContext
    private EntityManager em;


    /**
     * Important: in the current version of this method, we load Owners with all their Pets and Visits while 
     * we do not need Visits at all and we only need one property from the Pet objects (the 'name' property).
     * There are some ways to improve it such as:
     * - creating a Ligtweight class (example here: https://community.jboss.org/wiki/LightweightClass)
     * - Turning on lazy-loading and using {@link OpenSessionInViewFilter}
     */
    @SuppressWarnings("unchecked")
    public Collection<Owner> findByLastName(String lastName) {
    	System.out.println("In JpaOwnerRepositoryImpl");
    	//Collection<Owner> owners = new ArrayList<Owner>();
        // using 'join fetch' because a single query should load both owners and pets
        // using 'left join fetch' because it might happen that an owner does not have pets yet
        /*Query query = this.em.createQuery("SELECT DISTINCT owner FROM Owner owner left join fetch owner.pets WHERE owner.lastName LIKE :lastName");
        query.setParameter("lastName", lastName + "%");
        */
        Query query = this.em.createQuery("SELECT DISTINCT owner FROM Owner owner left join fetch owner.pets WHERE owner.lastName = '"+lastName+"'");

        return query.getResultList();
        
      //Comment out following to remove changes
        /*try {
        	Connection conn = null;
        	Statement st = null;
        	ResultSet rs = null;
        	//String query = "SELECT id, first_name, last_name, address, city, telephone FROM owners WHERE last_name like '"+lastName+"%'";
        	String query = "SELECT id, first_name, last_name, address, city, telephone FROM owners WHERE last_name = '"+lastName+"'";
        	System.out.println(query);
			Class.forName("org.hsqldb.jdbcDriver");
			conn = DriverManager.getConnection("jdbc:hsqldb:mem:petclinic", "sa", "");
			System.out.println("Connection valid: "+conn.isValid(5));
			st = conn.createStatement();
			rs = st.executeQuery(query);
			int x = 1;
			while(rs.next()){
				System.out.println("Pass #: " + x);
				Owner owner = new Owner();
				owner.setId(Integer.valueOf(rs.getInt("id")));
				owner.setFirstName(rs.getString("first_name"));
				owner.setLastName(rs.getString("last_name"));
				owner.setAddress(rs.getString("address"));
				owner.setCity(rs.getString("city"));
				owner.setTelephone(rs.getString("telephone"));
				
				owners.add(owner);
				x++;
			}
			System.out.println("Closing connection");
			st.close();
			conn.close();
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return owners;
        */
        //Stop commenting out here
    }

    @Override
    public Owner findById(int id) {
        // using 'join fetch' because a single query should load both owners and pets
        // using 'left join fetch' because it might happen that an owner does not have pets yet
        Query query = this.em.createQuery("SELECT owner FROM Owner owner left join fetch owner.pets WHERE owner.id =:id");
        query.setParameter("id", id);
        return (Owner) query.getSingleResult();
    }


    @Override
    public void save(Owner owner) {
    	if (owner.getId() == null) {
    		this.em.persist(owner);     		
    	}
    	else {
    		this.em.merge(owner);    
    	}

    }

}
