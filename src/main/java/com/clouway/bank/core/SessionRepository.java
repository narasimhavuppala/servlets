package com.clouway.bank.core;

import com.google.common.base.Optional;

/**
 * The implementation of this interface will be used to save and retrieve session data
 *
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public interface SessionRepository {
  /**
   * Will save session information
   *
   * @param session session
   */
  void save(Session session);

  /**
   * find session by email to user
   *
   * @param id session id
   * @return session
   */
  Optional<Session> findSessionById(String id);

  /**
   * Will remove session
   *
   * @param id session id
   */
  void remove(String id);
}