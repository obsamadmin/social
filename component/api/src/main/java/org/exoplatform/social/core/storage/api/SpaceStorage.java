/*
 * Copyright (C) 2003-2011 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.exoplatform.social.core.storage.api;

import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.social.core.model.SpaceExternalInvitation;
import org.exoplatform.social.core.relationship.model.Relationship;
import org.exoplatform.social.core.space.SpaceFilter;
import org.exoplatform.social.core.space.model.Space;
import org.exoplatform.social.core.storage.SpaceStorageException;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:alain.defrance@exoplatform.com">Alain Defrance</a>
 * @version $Revision$
 */
public interface SpaceStorage {

  /**
   * Gets a space by its display name.
   *
   * @param spaceDisplayName
   * @return the space with spaceDisplayName that matches the spaceDisplayName input.
   * @since  1.2.0-GA
   * @throws org.exoplatform.social.core.storage.SpaceStorageException
   */
  public Space getSpaceByDisplayName(String spaceDisplayName) throws SpaceStorageException;

  /**
   * Saves a space. If isNew is true, creates new space. If not only updates space
   * an saves it.
   *
   * @param space
   * @param isNew
   * @throws SpaceStorageException
   */
  public void saveSpace(Space space, boolean isNew) throws SpaceStorageException;

  /**
   * Renames a space.
   * 
   * @param space
   * @param newDisplayName
   * @throws SpaceStorageException
   * @since 1.2.8
   */
  public void renameSpace(Space space, String newDisplayName) throws SpaceStorageException;
  
  /**
   * Renames a space.
   * 
   * @param remoteId who update Space information
   * @param space
   * @param newDisplayName
   * @throws SpaceStorageException
   * @since 4.0.0
   */
  public void renameSpace(String remoteId, Space space, String newDisplayName) throws SpaceStorageException;
  
  /**
   * Deletes a space by space id.
   *
   * @param id
   * @throws SpaceStorageException
   */
  public void deleteSpace(String id) throws SpaceStorageException;

  /**
   * update the SpaceMembership between space and user
   *
   * @param spaceId the space Id
   * @param userId user Id
   */
   void ignoreSpace(String spaceId, String userId);

  /**
   * Verify if a space is ignored by the user or not.
   *
   * @param spaceId the id of the space
   * @param userId the id of the user
   * @return true if user has ignored the space from suggested list
   */

  boolean isSpaceIgnored(String spaceId, String userId);

  /**
   * Gets the count of the spaces that a user has the "manager" role.
   *
   * @param userId
   * @return the count of the manager spaces
   * @throws SpaceStorageException
   * @since 1.2.0-GA
   */
  public int getManagerSpacesCount(String userId);

  /**
   * Gets the count of the spaces which user has "manager" role by filter.
   *
   * @param userId
   * @param spaceFilter
   * @return
   * @since 1.2.0-GA
   */
  public int getManagerSpacesByFilterCount(String userId, SpaceFilter spaceFilter);

  /**
   * Gets the spaces that a user has the "manager" role with offset, limit.
   *
   * @param userId
   * @param offset
   * @param limit
   * @return a list of the manager spaces with offset, limit
   * @throws SpaceStorageException
   * @since 1.2.0-GA
   */
  public List<Space> getManagerSpaces(String userId, long offset, long limit);

  /**
   * Gets the manager spaces of the user id by the filter with offset, limit.
   *
   * @param userId
   * @param spaceFilter
   * @param offset
   * @param limit
   * @return
   * @since 1.2.0-GA
   */
  public List<Space> getManagerSpacesByFilter(String userId, SpaceFilter spaceFilter, long offset, long limit);

  /**
   * Gets the count of the spaces that a user has the "member" role.
   *
   * @param userId
   * @return the count of the member spaces
   * @throws SpaceStorageException
   * @since 1.2.0-GA
   */
  public int getMemberSpacesCount(String userId) throws SpaceStorageException;
  
  /**
   * Gets the count of the spaces which user has "member" role by filter.
   *
   * @param userId
   * @param spaceFilter
   * @return
   * @since 1.2.0-GA
   */
  public int getMemberSpacesByFilterCount(String userId, SpaceFilter spaceFilter);
  
  /**
   * Gets the spaces that a user has the "member" role.
   *
   * @param userId
   * @return a list of the member spaces
   * @throws SpaceStorageException
   * @deprecated use {@link SpaceStorage#getMemberSpaces(String, long, long)}
   *             instead with offset and limit
   * @since 1.2.0-GA
   */
  @Deprecated
  public List<Space> getMemberSpaces(String userId) throws SpaceStorageException;
  
  /**
   * Gets the spaces that a user has the "member" role with offset, limit.
   *
   * @param userId
   * @param offset
   * @param limit
   * @return a list of the member spaces with offset, limit
   * @throws SpaceStorageException
   * @since 1.2.0-GA
   */
  public List<Space> getMemberSpaces(String userId, long offset, long limit) throws SpaceStorageException;
  
  /**
   * Gets the member spaces of the user id by the filter with offset, limit.
   *
   * @param userId
   * @param spaceFilter
   * @param offset
   * @param limit
   * @return
   * @since 1.2.0-GA
   */
  public List<Space> getMemberSpacesByFilter(String userId, SpaceFilter spaceFilter, long offset, long limit);

  /**
   * Gets the count of the pending spaces of the userId.
   *
   * @param userId
   * @return the count of the pending spaces
   * @throws SpaceStorageException
   * @since 1.2.0-GA
   */
  public int getPendingSpacesCount(String userId) throws SpaceStorageException;

  /**
   * Gets the count of the pending spaces of the user by space filter.
   *
   * @param userId
   * @param spaceFilter
   * @return
   * @since 1.2.0-GA
   */
  public int getPendingSpacesByFilterCount(String userId, SpaceFilter spaceFilter);

  /**
   * Gets a user's pending spaces and that the user can revoke that request.
   *
   * @param userId
   * @return a list of the pending spaces
   * @throws SpaceStorageException
   * @since 1.2.0-GA
   */
  public List<Space> getPendingSpaces(String userId) throws SpaceStorageException;

  /**
   * Gets a user's pending spaces and that the user can revoke that request with offset, limit.
   *
   * @param userId
   * @param offset
   * @param limit
   * @return a list of the pending spaces with offset, limit
   * @throws SpaceStorageException
   * @since 1.2.0-GA
   */
  public List<Space> getPendingSpaces(String userId, long offset, long limit) throws SpaceStorageException;

  /**
   * Gets the pending spaces of the user by space filter with offset, limit.
   *
   * @param userId
   * @param spaceFilter
   * @param offset
   * @param limit
   * @return
   * @since 1.2.0-GA
   */
  public List<Space> getPendingSpacesByFilter(String userId, SpaceFilter spaceFilter, long offset, long limit);

  /**
   * Gets the count of the invited spaces of the userId.
   *
   * @param userId
   * @return the count of the invited spaces
   * @throws SpaceStorageException
   * @since 1.2.0-GA
   */
  public int getInvitedSpacesCount(String userId) throws SpaceStorageException;

  /**
   * Gets the count of the invited spaces of the user by filter.
   *
   * @param userId
   * @param spaceFilter
   * @return
   * @since 1.2.0-GA
   */
  public int getInvitedSpacesByFilterCount(String userId, SpaceFilter spaceFilter);

  /**
   * Gets a user's invited spaces and that user can accept or deny the request.
   *
   * @param userId
   * @return a list of the invited spaces.
   * @throws SpaceStorageException
   * @since 1.2.0-GA
   */
  public List<Space> getInvitedSpaces(String userId) throws SpaceStorageException;

  /**
   * Gets a user's invited spaces and that user can accept or deny the request with offset, limit.
   *
   * @param userId
   * @param offset
   * @param limit
   * @return a list of the invited spaces with offset, limit
   * @throws SpaceStorageException
   * @since 1.2.0-GA
   */
  public List<Space> getInvitedSpaces(String userId, long offset, long limit) throws SpaceStorageException;

  /**
   * Gets the invited spaces of the user by space filter with offset, limit.
   *
   * @param userId
   * @param spaceFilter
   * @param offset
   * @param limit
   * @return
   * @since 1.2.0-GA
   */
  public List<Space> getInvitedSpacesByFilter(String userId, SpaceFilter spaceFilter, long offset, long limit);

  /**
   * Gets the count of the public spaces of the userId.
   *
   * @param userId
   * @return the count of the spaces in which the user can request to join
   * @throws SpaceStorageException
   * @since 1.2.0-GA
   */
  public int getPublicSpacesCount(String userId) throws SpaceStorageException;
  
  /**
   * Gets the count of the public spaces of the user by space filter.
   *
   * @param userId
   * @param spaceFilter
   * @return
   * @since 1.2.0-GA
   */
  public int getPublicSpacesByFilterCount(String userId, SpaceFilter spaceFilter);

  /**
   * Gets the public spaces of the user by filter with offset, limit.
   *
   * @param userId
   * @param spaceFilter
   * @param offset
   * @param limit
   * @return
   * @since 1.2.0-GA
   */
  public List<Space> getPublicSpacesByFilter(String userId, SpaceFilter spaceFilter, long offset, long limit);

  /**
   * Gets a user's public spaces and that user can request to join.
   *
   * @param userId
   * @return spaces list in which the user can request to join.
   * @throws SpaceStorageException
   * @since 1.2.0-GA
   */
  public List<Space> getPublicSpaces(String userId) throws SpaceStorageException;

  /**
   * Gets a user's public spaces and that user can request to join with offset, limit.
   *
   * @param userId
   * @param offset
   * @param limit
   * @return spaces list in which the user can request to join with offset, limit
   * @throws SpaceStorageException
   * @since 1.2.0-GA
   */
  public List<Space> getPublicSpaces(String userId, long offset, long limit) throws SpaceStorageException;

  /**
   * Gets the count of the accessible spaces of the userId.
   *
   * @param userId
   * @return the count of the accessible spaces
   * @throws SpaceStorageException
   * @since 1.2.0-GA
   */
  public int getAccessibleSpacesCount(String userId) throws SpaceStorageException;
  
  /**
   * Gets the count of the visible spaces of the userId.
   * 
   * @param userId
   * @param spaceFilter
   * @return
   * @throws SpaceStorageException
   * @since 1.2.5-GA
   */
  public int getVisibleSpacesCount(String userId, SpaceFilter spaceFilter) throws SpaceStorageException;
  
  /**
   * Provides Unified Search to get the count of the visible spaces of the userId.
   * 
   * @param userId
   * @param spaceFilter
   * @return
   * @throws SpaceStorageException
   * @since 4.0.0-GA
   */
  public int getUnifiedSearchSpacesCount(String userId, SpaceFilter spaceFilter) throws SpaceStorageException;

  /**
   * Gets the count of the accessible spaces of the user by filter.
   *
   * @param userId
   * @param spaceFilter
   * @return
   * @since 1.2.0-GA
   */
  public int getAccessibleSpacesByFilterCount(String userId, SpaceFilter spaceFilter);

  /**
   * Gets the spaces of a user which that user has the "member" role or edit permission.
   *
   * @param userId the userId
   * @return a list of the accessible spaces
   * @throws SpaceStorageException
   * @since 1.2.0-GA
   * @deprecated 4.0.0-RC2
   */
  @Deprecated
  public List<Space> getAccessibleSpaces(String userId) throws SpaceStorageException;
  
  /**
   * Gets the spaces of a user which that user has the visible spaces.
   *
   * @param userId the userId
   * @return a list of the accessible spaces
   * @throws SpaceStorageException
   * @since 1.2.5-GA
   */
  public List<Space> getVisibleSpaces(String userId, SpaceFilter spaceFilter, long offset, long limit)
                                      throws SpaceStorageException;
  
  /**
   * Provides Unified Search to get the spaces of a user which that user has the visible spaces.
   *
   * @param userId the userId
   * @return a list of the accessible spaces
   * @throws SpaceStorageException
   * @since 4.0.0-GA
   */
  public List<Space> getUnifiedSearchSpaces(String userId, SpaceFilter spaceFilter, long offset, long limit)
                                      throws SpaceStorageException;
  
  /**
   * Gets the spaces of a user which that user has the "member" role or edit permission.
   *
   * @param userId the userId
   * @return a list of the accessible spaces
   * @throws SpaceStorageException
   * @since 1.2.0-GA
   */
  public List<Space> getVisibleSpaces(String userId, SpaceFilter spaceFilter) throws SpaceStorageException;

  /**
   * Gets the spaces of a user which that user has "member" role or edit permission with offset, limit.
   *
   * @param userId the userId
   * @param offset
   * @param limit
   * @return a list of the accessible space with offset, limit
   * @throws SpaceStorageException
   * @since 1.2.0-GA
   */
  public List<Space> getAccessibleSpaces(String userId, long offset, long limit) throws SpaceStorageException;

  /**
   * Gets the accessible spaces of the user by filter with offset, limit.
   *
   * @param userId
   * @param spaceFilter
   * @param offset
   * @param limit
   * @return
   * @since 1.2.0-GA
   */
  public List<Space> getAccessibleSpacesByFilter(String userId, SpaceFilter spaceFilter, long offset, long limit);

  /**
   * Gets the count of the spaces of a user which that user has the edit permission.
   *
   * @param userId
   * @return the count of the editable spaces.
   * @throws SpaceStorageException
   * @since 1.2.0-GA
   */
  public int getEditableSpacesCount(String userId) throws SpaceStorageException;

  /**
   * Gets the count of the editable spaces of the user by filter.
   *
   * @param userId
   * @param spaceFilter
   * @return
   * @since 1.2.0-GA
   */
  public int getEditableSpacesByFilterCount(String userId, SpaceFilter spaceFilter);

  /**
   * Gets the spaces of a user which that user has the edit permission.
   *
   * @param userId
   * @return a list of the editable spaces.
   * @throws SpaceStorageException
   * @since 1.2.0-GA
   */
  public List<Space> getEditableSpaces(String userId) throws SpaceStorageException;

  /**
   * Gets the spaces of a user which that user has the edit permission with offset, limit.
   *
   * @param userId
   * @param offset
   * @param limit
   * @return a list of the spaces with offset, limit
   * @throws SpaceStorageException
   * @since 1.2.0-GA
   */
  public List<Space> getEditableSpaces(String userId, long offset, long limit) throws SpaceStorageException;

  /**
   * Gets the editable spaces of the user by filter with offset, limit.
   *
   * @param userId
   * @param spaceFilter
   * @param offset
   * @param limit
   * @return
   * @since 1.2.0-GA
   */
  public List<Space> getEditableSpacesByFilter(String userId, SpaceFilter spaceFilter, long offset, long limit);

  /**
   * Gets the count of the spaces.
   *
   * @return the count of all spaces
   * @throws SpaceStorageException
   * @since 1.2.0-GA
   */
  public int getAllSpacesCount() throws SpaceStorageException;

  /**
   * Gets all the spaces. By the default get the all spaces with OFFSET = 0, LIMIT = 200;
   *
   * @throws SpaceStorageException
   * @return the list of all spaces
   */
  public List<Space> getAllSpaces() throws SpaceStorageException;

  /**
   * Gets the count of the spaces which are searched by space filter.
   *
   * @param spaceFilter
   * @return
   * @since 1.2.0-GA
   */
  public int getAllSpacesByFilterCount(SpaceFilter spaceFilter);

  /**
   * Gets the spaces with offset, limit.
   *
   * @param offset
   * @param limit
   * @return the list of the spaces with offset, limit
   * @throws SpaceStorageException
   * @since 1.2.0-GA
   */
  public List<Space> getSpaces(long offset, long limit) throws SpaceStorageException;

  /**
   * Gets the spaces by space filter with offset, limit.
   *
   * @param spaceFilter
   * @param offset
   * @param limit
   * @return
   * @throws SpaceStorageException
   */
  public List<Space> getSpacesByFilter(SpaceFilter spaceFilter, long offset, long limit);

  /**
   * Gets a space by its space id.
   *
   * @param id
   * @return space with id specified
   * @throws SpaceStorageException
   */
  public Space getSpaceById(String id) throws SpaceStorageException;
  
  /**
   * Gets a space simple by its space id to aim decrease workload to get full information.
   * 
   * It's only to get a little space information such as displayName, groupId, prettyName
   * description, id, avatar ..
   *
   * @param id
   * @return space with id specified
   * @throws SpaceStorageException
   */
  public Space getSpaceSimpleById(String id) throws SpaceStorageException;

  /**
   * Gets a space by its pretty name.
   *
   * @param spacePrettyName
   * @return the space with spacePrettyName that matches spacePrettyName input.
   * @throws SpaceStorageException
   * @since 1.2.0-GA
   */
  public Space getSpaceByPrettyName(String spacePrettyName) throws SpaceStorageException;

  /**
   * Gets a space by its associated group id.
   *
   * @param  groupId
   * @return the space that has group id matching the groupId string input.
   * @throws SpaceStorageException
   * @since  1.2.0-GA
   */
  public Space getSpaceByGroupId(String groupId) throws SpaceStorageException;

  /**
   * Gets a space by its url.
   *
   * @param url
   * @return the space with string url specified
   * @throws SpaceStorageException
   */
  public Space getSpaceByUrl(String url) throws SpaceStorageException;
  
  /**
   * Update accessed space to top of space members list of Identity model
   *
   * @param remoteId
   * @param space
   */
  void updateSpaceAccessed(String remoteId, Space space) throws SpaceStorageException;
  
  /**
   * Gets list of spaces which user has been last visited.
   * @param offset TODO
   * @param limit
   * @param filter
   */
  List<Space> getLastAccessedSpace(SpaceFilter filter, int offset, int limit) throws SpaceStorageException;
  
  /**
   * Gets number of spaces which user has been last visited.
   * @param filter
   */
  int getLastAccessedSpaceCount(SpaceFilter filter) throws SpaceStorageException;
  
  /**
   * Gets the count of the public spaces of the userId.
   *
   * @param userId
   * @return number of public space of a user where he is member
   * @since 4.0.0.Beta01
   */
  int getNumberOfMemberPublicSpaces(String userId);
  
  /**
   * Get the visited spaces
   * 
   * @param filter
   * @param offset
   * @param limit
   * @return list of browsed spaces
   * @throws SpaceStorageException
   */
  List<Space> getVisitedSpaces(SpaceFilter filter, int offset, int limit) throws SpaceStorageException;

  /**
   * Gets the last spaces that have been created.
   *
   * @param limit the limit of spaces to provide.
   * @return The last spaces.
   * @LevelAPI Experimental
   * @since 4.0.x
   */
  List<Space> getLastSpaces(int limit);
  
  /**
   * @param identityId
   * @param offset
   * @param limit
   * @return
   * @throws SpaceStorageException
   */
  public List<String> getMemberSpaceIds(String identityId, int offset, int limit) throws SpaceStorageException;

  /**
   * @param spaceId
   * @return
   * @throws SpaceStorageException
   */
  public List<SpaceExternalInvitation> findSpaceExternalInvitationsBySpaceId(String spaceId) throws SpaceStorageException;

  /**
   * Saves a new external invitation in space
   *
   * @param spaceId
   * @param email
   * @throws SpaceStorageException
   */
  public void saveSpaceExternalInvitation(String spaceId, String email, String tokenId) throws SpaceStorageException;

  /**
   * Get an external invitation by invitation id
   *
   * @param invitationId
   * @throws SpaceStorageException
   */
  public SpaceExternalInvitation findSpaceExternalInvitationById(String invitationId) throws SpaceStorageException;

  /**
   * Delete an external invitation from a space
   *
   * @param spaceExternalInvitation
   * @throws SpaceStorageException
   */
  public void deleteSpaceExternalInvitation(SpaceExternalInvitation spaceExternalInvitation) throws SpaceStorageException;

  /**
   * get the list of spaces ids by external email
   *
   * @param email
   * @throws SpaceStorageException
   */
  public List<String> findExternalInvitationsSpacesByEmail(String email) throws SpaceStorageException;

  /**
   * Delete external invitations
   *
   * @param email
   * @throws SpaceStorageException
   */
  public void deleteExternalUserInvitations(String email) throws SpaceStorageException;

  /**
   * @param username username used to retrieve user spaces
   * @return the count of users requested to join spaces that user manages
   */
  default int countPendingSpaceRequestsToManage(String username) {
    throw new UnsupportedOperationException();
  }

  /**
   * @param username username used to retrieve user spaces
   * @param offset offset of the query
   * @param limit limit of the query
   * @return {@link List} of {@link Space} with pending users requesting to
   *         join spaces that the designated user manages
   */
  default List<Space> getPendingSpaceRequestsToManage(String username, int offset, int limit) {
    throw new UnsupportedOperationException();
  }

  /**
   * Counts the number of external users in a specific space
   *
   * @param spaceId
   * @return counts the external members in the space
   */
  default int countExternalMembers(Long spaceId) {
    throw new UnsupportedOperationException();
  }

  /**
   * Get common spaces between two users
   *
   * @param userId        connected user id
   * @param otherUserId visited profile user id
   * @return list of common spaces between two users in param
   */
  default List<Space> getCommonSpaces(String userId, String otherUserId, int offset, int limit){
    throw new UnsupportedOperationException();
  }
  /**
   * Count common spaces between two users
   *
   * @param userId        connected user id
   * @param otherUserId visited profile user id
   * @return list of common spaces between two users in param
   */
  default int countCommonSpaces(String userId, String otherUserId){
    throw new UnsupportedOperationException();
  }
}
