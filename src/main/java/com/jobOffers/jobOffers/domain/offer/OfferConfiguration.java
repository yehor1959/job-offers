package com.jobOffers.jobOffers.domain.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Configuration
public class OfferConfiguration {

//    @Bean
//    OfferRepository offerRepository() {
//        return new OfferRepository() {
//            @Override
//            public boolean existsByOfferUrl(String offerUrl) {
//                return false;
//            }
//
//            @Override
//            public Optional<Offer> findByOfferUrl(String offerUrl) {
//                return Optional.empty();
//            }
//
//            @Override
//            public <S extends Offer> List<S> saveAll(Iterable<S> entities) {
//                return List.of();
//            }
//
//            @Override
//            public Optional<Offer> findById(Long aLong) {
//                return Optional.empty();
//            }
//
//            @Override
//            public boolean existsById(Long aLong) {
//                return false;
//            }
//
//            @Override
//            public List<Offer> findAll() {
//                return List.of();
//            }
//
//            @Override
//            public Iterable<Offer> findAllById(Iterable<Long> longs) {
//                return null;
//            }
//
//            @Override
//            public long count() {
//                return 0;
//            }
//
//            @Override
//            public void deleteById(Long aLong) {
//
//            }
//
//            @Override
//            public void delete(Offer entity) {
//
//            }
//
//            @Override
//            public void deleteAllById(Iterable<? extends Long> longs) {
//
//            }
//
//            @Override
//            public void deleteAll(Iterable<? extends Offer> entities) {
//
//            }
//
//            @Override
//            public void deleteAll() {
//
//            }
//
//            @Override
//            public List<Offer> findAll(Sort sort) {
//                return List.of();
//            }
//
//            @Override
//            public Page<Offer> findAll(Pageable pageable) {
//                return null;
//            }
//
//            @Override
//            public <S extends Offer> S insert(S entity) {
//                return null;
//            }
//
//            @Override
//            public <S extends Offer> List<S> insert(Iterable<S> entities) {
//                return List.of();
//            }
//
//            @Override
//            public <S extends Offer> Optional<S> findOne(Example<S> example) {
//                return Optional.empty();
//            }
//
//            @Override
//            public <S extends Offer> List<S> findAll(Example<S> example) {
//                return List.of();
//            }
//
//            @Override
//            public <S extends Offer> List<S> findAll(Example<S> example, Sort sort) {
//                return List.of();
//            }
//
//            @Override
//            public <S extends Offer> Page<S> findAll(Example<S> example, Pageable pageable) {
//                return null;
//            }
//
//            @Override
//            public <S extends Offer> long count(Example<S> example) {
//                return 0;
//            }
//
//            @Override
//            public <S extends Offer> boolean exists(Example<S> example) {
//                return false;
//            }
//
//            @Override
//            public <S extends Offer, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
//                return null;
//            }

//            @Override
//            public List<Offer> saveAll(List<Offer> offers) {
//                return List.of();
//            }
//
//            @Override
//            public Optional<Offer> findById(String id) {
//                return Optional.empty();
//            }
//
//            @Override
//            public Offer save(Offer offer) {
//                return null;
//            }
//        };
//    }

    @Bean
    OfferService offerService(OfferFetcher fetcher, OfferRepository offerRepository) {
        return new OfferService(fetcher, offerRepository);
    }

    @Bean
    OfferFacade offerFacade(OfferRepository offerRepository, OfferService offerService) {
        return new OfferFacade(offerRepository, offerService);
    }
}
