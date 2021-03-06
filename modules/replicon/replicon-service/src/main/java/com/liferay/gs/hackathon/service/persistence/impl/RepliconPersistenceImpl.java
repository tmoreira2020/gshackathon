/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.gs.hackathon.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.gs.hackathon.exception.NoSuchRepliconException;
import com.liferay.gs.hackathon.model.Replicon;
import com.liferay.gs.hackathon.model.impl.RepliconImpl;
import com.liferay.gs.hackathon.model.impl.RepliconModelImpl;
import com.liferay.gs.hackathon.service.persistence.RepliconPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the replicon service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RepliconPersistence
 * @see com.liferay.gs.hackathon.service.persistence.RepliconUtil
 * @generated
 */
@ProviderType
public class RepliconPersistenceImpl extends BasePersistenceImpl<Replicon>
	implements RepliconPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link RepliconUtil} to access the replicon persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = RepliconImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(RepliconModelImpl.ENTITY_CACHE_ENABLED,
			RepliconModelImpl.FINDER_CACHE_ENABLED, RepliconImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(RepliconModelImpl.ENTITY_CACHE_ENABLED,
			RepliconModelImpl.FINDER_CACHE_ENABLED, RepliconImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(RepliconModelImpl.ENTITY_CACHE_ENABLED,
			RepliconModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(RepliconModelImpl.ENTITY_CACHE_ENABLED,
			RepliconModelImpl.FINDER_CACHE_ENABLED, RepliconImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(RepliconModelImpl.ENTITY_CACHE_ENABLED,
			RepliconModelImpl.FINDER_CACHE_ENABLED, RepliconImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			RepliconModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(RepliconModelImpl.ENTITY_CACHE_ENABLED,
			RepliconModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the replicons where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching replicons
	 */
	@Override
	public List<Replicon> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the replicons where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepliconModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of replicons
	 * @param end the upper bound of the range of replicons (not inclusive)
	 * @return the range of matching replicons
	 */
	@Override
	public List<Replicon> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the replicons where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepliconModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of replicons
	 * @param end the upper bound of the range of replicons (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching replicons
	 */
	@Override
	public List<Replicon> findByUuid(String uuid, int start, int end,
		OrderByComparator<Replicon> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the replicons where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepliconModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of replicons
	 * @param end the upper bound of the range of replicons (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching replicons
	 */
	@Override
	public List<Replicon> findByUuid(String uuid, int start, int end,
		OrderByComparator<Replicon> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<Replicon> list = null;

		if (retrieveFromCache) {
			list = (List<Replicon>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Replicon replicon : list) {
					if (!Objects.equals(uuid, replicon.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_REPLICON_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(RepliconModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<Replicon>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Replicon>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first replicon in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching replicon
	 * @throws NoSuchRepliconException if a matching replicon could not be found
	 */
	@Override
	public Replicon findByUuid_First(String uuid,
		OrderByComparator<Replicon> orderByComparator)
		throws NoSuchRepliconException {
		Replicon replicon = fetchByUuid_First(uuid, orderByComparator);

		if (replicon != null) {
			return replicon;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRepliconException(msg.toString());
	}

	/**
	 * Returns the first replicon in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching replicon, or <code>null</code> if a matching replicon could not be found
	 */
	@Override
	public Replicon fetchByUuid_First(String uuid,
		OrderByComparator<Replicon> orderByComparator) {
		List<Replicon> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last replicon in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching replicon
	 * @throws NoSuchRepliconException if a matching replicon could not be found
	 */
	@Override
	public Replicon findByUuid_Last(String uuid,
		OrderByComparator<Replicon> orderByComparator)
		throws NoSuchRepliconException {
		Replicon replicon = fetchByUuid_Last(uuid, orderByComparator);

		if (replicon != null) {
			return replicon;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRepliconException(msg.toString());
	}

	/**
	 * Returns the last replicon in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching replicon, or <code>null</code> if a matching replicon could not be found
	 */
	@Override
	public Replicon fetchByUuid_Last(String uuid,
		OrderByComparator<Replicon> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<Replicon> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the replicons before and after the current replicon in the ordered set where uuid = &#63;.
	 *
	 * @param projectId the primary key of the current replicon
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next replicon
	 * @throws NoSuchRepliconException if a replicon with the primary key could not be found
	 */
	@Override
	public Replicon[] findByUuid_PrevAndNext(long projectId, String uuid,
		OrderByComparator<Replicon> orderByComparator)
		throws NoSuchRepliconException {
		Replicon replicon = findByPrimaryKey(projectId);

		Session session = null;

		try {
			session = openSession();

			Replicon[] array = new RepliconImpl[3];

			array[0] = getByUuid_PrevAndNext(session, replicon, uuid,
					orderByComparator, true);

			array[1] = replicon;

			array[2] = getByUuid_PrevAndNext(session, replicon, uuid,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Replicon getByUuid_PrevAndNext(Session session,
		Replicon replicon, String uuid,
		OrderByComparator<Replicon> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_REPLICON_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(RepliconModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(replicon);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Replicon> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the replicons where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (Replicon replicon : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(replicon);
		}
	}

	/**
	 * Returns the number of replicons where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching replicons
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_REPLICON_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "replicon.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "replicon.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(replicon.uuid IS NULL OR replicon.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(RepliconModelImpl.ENTITY_CACHE_ENABLED,
			RepliconModelImpl.FINDER_CACHE_ENABLED, RepliconImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			RepliconModelImpl.UUID_COLUMN_BITMASK |
			RepliconModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(RepliconModelImpl.ENTITY_CACHE_ENABLED,
			RepliconModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the replicon where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchRepliconException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching replicon
	 * @throws NoSuchRepliconException if a matching replicon could not be found
	 */
	@Override
	public Replicon findByUUID_G(String uuid, long groupId)
		throws NoSuchRepliconException {
		Replicon replicon = fetchByUUID_G(uuid, groupId);

		if (replicon == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchRepliconException(msg.toString());
		}

		return replicon;
	}

	/**
	 * Returns the replicon where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching replicon, or <code>null</code> if a matching replicon could not be found
	 */
	@Override
	public Replicon fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the replicon where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching replicon, or <code>null</code> if a matching replicon could not be found
	 */
	@Override
	public Replicon fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof Replicon) {
			Replicon replicon = (Replicon)result;

			if (!Objects.equals(uuid, replicon.getUuid()) ||
					(groupId != replicon.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_REPLICON_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<Replicon> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					Replicon replicon = list.get(0);

					result = replicon;

					cacheResult(replicon);

					if ((replicon.getUuid() == null) ||
							!replicon.getUuid().equals(uuid) ||
							(replicon.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, replicon);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (Replicon)result;
		}
	}

	/**
	 * Removes the replicon where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the replicon that was removed
	 */
	@Override
	public Replicon removeByUUID_G(String uuid, long groupId)
		throws NoSuchRepliconException {
		Replicon replicon = findByUUID_G(uuid, groupId);

		return remove(replicon);
	}

	/**
	 * Returns the number of replicons where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching replicons
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_REPLICON_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "replicon.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "replicon.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(replicon.uuid IS NULL OR replicon.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "replicon.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(RepliconModelImpl.ENTITY_CACHE_ENABLED,
			RepliconModelImpl.FINDER_CACHE_ENABLED, RepliconImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(RepliconModelImpl.ENTITY_CACHE_ENABLED,
			RepliconModelImpl.FINDER_CACHE_ENABLED, RepliconImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			RepliconModelImpl.UUID_COLUMN_BITMASK |
			RepliconModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(RepliconModelImpl.ENTITY_CACHE_ENABLED,
			RepliconModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the replicons where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching replicons
	 */
	@Override
	public List<Replicon> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the replicons where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepliconModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of replicons
	 * @param end the upper bound of the range of replicons (not inclusive)
	 * @return the range of matching replicons
	 */
	@Override
	public List<Replicon> findByUuid_C(String uuid, long companyId, int start,
		int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the replicons where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepliconModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of replicons
	 * @param end the upper bound of the range of replicons (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching replicons
	 */
	@Override
	public List<Replicon> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<Replicon> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the replicons where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepliconModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of replicons
	 * @param end the upper bound of the range of replicons (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching replicons
	 */
	@Override
	public List<Replicon> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<Replicon> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<Replicon> list = null;

		if (retrieveFromCache) {
			list = (List<Replicon>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Replicon replicon : list) {
					if (!Objects.equals(uuid, replicon.getUuid()) ||
							(companyId != replicon.getCompanyId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_REPLICON_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(RepliconModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				if (!pagination) {
					list = (List<Replicon>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Replicon>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first replicon in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching replicon
	 * @throws NoSuchRepliconException if a matching replicon could not be found
	 */
	@Override
	public Replicon findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<Replicon> orderByComparator)
		throws NoSuchRepliconException {
		Replicon replicon = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (replicon != null) {
			return replicon;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRepliconException(msg.toString());
	}

	/**
	 * Returns the first replicon in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching replicon, or <code>null</code> if a matching replicon could not be found
	 */
	@Override
	public Replicon fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<Replicon> orderByComparator) {
		List<Replicon> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last replicon in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching replicon
	 * @throws NoSuchRepliconException if a matching replicon could not be found
	 */
	@Override
	public Replicon findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<Replicon> orderByComparator)
		throws NoSuchRepliconException {
		Replicon replicon = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (replicon != null) {
			return replicon;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRepliconException(msg.toString());
	}

	/**
	 * Returns the last replicon in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching replicon, or <code>null</code> if a matching replicon could not be found
	 */
	@Override
	public Replicon fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<Replicon> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<Replicon> list = findByUuid_C(uuid, companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the replicons before and after the current replicon in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param projectId the primary key of the current replicon
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next replicon
	 * @throws NoSuchRepliconException if a replicon with the primary key could not be found
	 */
	@Override
	public Replicon[] findByUuid_C_PrevAndNext(long projectId, String uuid,
		long companyId, OrderByComparator<Replicon> orderByComparator)
		throws NoSuchRepliconException {
		Replicon replicon = findByPrimaryKey(projectId);

		Session session = null;

		try {
			session = openSession();

			Replicon[] array = new RepliconImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, replicon, uuid,
					companyId, orderByComparator, true);

			array[1] = replicon;

			array[2] = getByUuid_C_PrevAndNext(session, replicon, uuid,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Replicon getByUuid_C_PrevAndNext(Session session,
		Replicon replicon, String uuid, long companyId,
		OrderByComparator<Replicon> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_REPLICON_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(RepliconModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(replicon);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Replicon> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the replicons where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (Replicon replicon : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(replicon);
		}
	}

	/**
	 * Returns the number of replicons where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching replicons
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_REPLICON_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "replicon.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "replicon.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(replicon.uuid IS NULL OR replicon.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "replicon.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PROJECTNAME =
		new FinderPath(RepliconModelImpl.ENTITY_CACHE_ENABLED,
			RepliconModelImpl.FINDER_CACHE_ENABLED, RepliconImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByProjectName",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PROJECTNAME =
		new FinderPath(RepliconModelImpl.ENTITY_CACHE_ENABLED,
			RepliconModelImpl.FINDER_CACHE_ENABLED, RepliconImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByProjectName",
			new String[] { String.class.getName() },
			RepliconModelImpl.PROJECTNAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PROJECTNAME = new FinderPath(RepliconModelImpl.ENTITY_CACHE_ENABLED,
			RepliconModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByProjectName",
			new String[] { String.class.getName() });

	/**
	 * Returns all the replicons where projectName = &#63;.
	 *
	 * @param projectName the project name
	 * @return the matching replicons
	 */
	@Override
	public List<Replicon> findByProjectName(String projectName) {
		return findByProjectName(projectName, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the replicons where projectName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepliconModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param projectName the project name
	 * @param start the lower bound of the range of replicons
	 * @param end the upper bound of the range of replicons (not inclusive)
	 * @return the range of matching replicons
	 */
	@Override
	public List<Replicon> findByProjectName(String projectName, int start,
		int end) {
		return findByProjectName(projectName, start, end, null);
	}

	/**
	 * Returns an ordered range of all the replicons where projectName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepliconModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param projectName the project name
	 * @param start the lower bound of the range of replicons
	 * @param end the upper bound of the range of replicons (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching replicons
	 */
	@Override
	public List<Replicon> findByProjectName(String projectName, int start,
		int end, OrderByComparator<Replicon> orderByComparator) {
		return findByProjectName(projectName, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the replicons where projectName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepliconModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param projectName the project name
	 * @param start the lower bound of the range of replicons
	 * @param end the upper bound of the range of replicons (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching replicons
	 */
	@Override
	public List<Replicon> findByProjectName(String projectName, int start,
		int end, OrderByComparator<Replicon> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PROJECTNAME;
			finderArgs = new Object[] { projectName };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_PROJECTNAME;
			finderArgs = new Object[] { projectName, start, end, orderByComparator };
		}

		List<Replicon> list = null;

		if (retrieveFromCache) {
			list = (List<Replicon>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Replicon replicon : list) {
					if (!Objects.equals(projectName, replicon.getProjectName())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_REPLICON_WHERE);

			boolean bindProjectName = false;

			if (projectName == null) {
				query.append(_FINDER_COLUMN_PROJECTNAME_PROJECTNAME_1);
			}
			else if (projectName.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_PROJECTNAME_PROJECTNAME_3);
			}
			else {
				bindProjectName = true;

				query.append(_FINDER_COLUMN_PROJECTNAME_PROJECTNAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(RepliconModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindProjectName) {
					qPos.add(projectName);
				}

				if (!pagination) {
					list = (List<Replicon>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Replicon>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first replicon in the ordered set where projectName = &#63;.
	 *
	 * @param projectName the project name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching replicon
	 * @throws NoSuchRepliconException if a matching replicon could not be found
	 */
	@Override
	public Replicon findByProjectName_First(String projectName,
		OrderByComparator<Replicon> orderByComparator)
		throws NoSuchRepliconException {
		Replicon replicon = fetchByProjectName_First(projectName,
				orderByComparator);

		if (replicon != null) {
			return replicon;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("projectName=");
		msg.append(projectName);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRepliconException(msg.toString());
	}

	/**
	 * Returns the first replicon in the ordered set where projectName = &#63;.
	 *
	 * @param projectName the project name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching replicon, or <code>null</code> if a matching replicon could not be found
	 */
	@Override
	public Replicon fetchByProjectName_First(String projectName,
		OrderByComparator<Replicon> orderByComparator) {
		List<Replicon> list = findByProjectName(projectName, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last replicon in the ordered set where projectName = &#63;.
	 *
	 * @param projectName the project name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching replicon
	 * @throws NoSuchRepliconException if a matching replicon could not be found
	 */
	@Override
	public Replicon findByProjectName_Last(String projectName,
		OrderByComparator<Replicon> orderByComparator)
		throws NoSuchRepliconException {
		Replicon replicon = fetchByProjectName_Last(projectName,
				orderByComparator);

		if (replicon != null) {
			return replicon;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("projectName=");
		msg.append(projectName);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRepliconException(msg.toString());
	}

	/**
	 * Returns the last replicon in the ordered set where projectName = &#63;.
	 *
	 * @param projectName the project name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching replicon, or <code>null</code> if a matching replicon could not be found
	 */
	@Override
	public Replicon fetchByProjectName_Last(String projectName,
		OrderByComparator<Replicon> orderByComparator) {
		int count = countByProjectName(projectName);

		if (count == 0) {
			return null;
		}

		List<Replicon> list = findByProjectName(projectName, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the replicons before and after the current replicon in the ordered set where projectName = &#63;.
	 *
	 * @param projectId the primary key of the current replicon
	 * @param projectName the project name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next replicon
	 * @throws NoSuchRepliconException if a replicon with the primary key could not be found
	 */
	@Override
	public Replicon[] findByProjectName_PrevAndNext(long projectId,
		String projectName, OrderByComparator<Replicon> orderByComparator)
		throws NoSuchRepliconException {
		Replicon replicon = findByPrimaryKey(projectId);

		Session session = null;

		try {
			session = openSession();

			Replicon[] array = new RepliconImpl[3];

			array[0] = getByProjectName_PrevAndNext(session, replicon,
					projectName, orderByComparator, true);

			array[1] = replicon;

			array[2] = getByProjectName_PrevAndNext(session, replicon,
					projectName, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Replicon getByProjectName_PrevAndNext(Session session,
		Replicon replicon, String projectName,
		OrderByComparator<Replicon> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_REPLICON_WHERE);

		boolean bindProjectName = false;

		if (projectName == null) {
			query.append(_FINDER_COLUMN_PROJECTNAME_PROJECTNAME_1);
		}
		else if (projectName.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_PROJECTNAME_PROJECTNAME_3);
		}
		else {
			bindProjectName = true;

			query.append(_FINDER_COLUMN_PROJECTNAME_PROJECTNAME_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(RepliconModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindProjectName) {
			qPos.add(projectName);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(replicon);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Replicon> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the replicons where projectName = &#63; from the database.
	 *
	 * @param projectName the project name
	 */
	@Override
	public void removeByProjectName(String projectName) {
		for (Replicon replicon : findByProjectName(projectName,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(replicon);
		}
	}

	/**
	 * Returns the number of replicons where projectName = &#63;.
	 *
	 * @param projectName the project name
	 * @return the number of matching replicons
	 */
	@Override
	public int countByProjectName(String projectName) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_PROJECTNAME;

		Object[] finderArgs = new Object[] { projectName };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_REPLICON_WHERE);

			boolean bindProjectName = false;

			if (projectName == null) {
				query.append(_FINDER_COLUMN_PROJECTNAME_PROJECTNAME_1);
			}
			else if (projectName.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_PROJECTNAME_PROJECTNAME_3);
			}
			else {
				bindProjectName = true;

				query.append(_FINDER_COLUMN_PROJECTNAME_PROJECTNAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindProjectName) {
					qPos.add(projectName);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_PROJECTNAME_PROJECTNAME_1 = "replicon.projectName IS NULL";
	private static final String _FINDER_COLUMN_PROJECTNAME_PROJECTNAME_2 = "replicon.projectName = ?";
	private static final String _FINDER_COLUMN_PROJECTNAME_PROJECTNAME_3 = "(replicon.projectName IS NULL OR replicon.projectName = '')";

	public RepliconPersistenceImpl() {
		setModelClass(Replicon.class);
	}

	/**
	 * Caches the replicon in the entity cache if it is enabled.
	 *
	 * @param replicon the replicon
	 */
	@Override
	public void cacheResult(Replicon replicon) {
		entityCache.putResult(RepliconModelImpl.ENTITY_CACHE_ENABLED,
			RepliconImpl.class, replicon.getPrimaryKey(), replicon);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { replicon.getUuid(), replicon.getGroupId() }, replicon);

		replicon.resetOriginalValues();
	}

	/**
	 * Caches the replicons in the entity cache if it is enabled.
	 *
	 * @param replicons the replicons
	 */
	@Override
	public void cacheResult(List<Replicon> replicons) {
		for (Replicon replicon : replicons) {
			if (entityCache.getResult(RepliconModelImpl.ENTITY_CACHE_ENABLED,
						RepliconImpl.class, replicon.getPrimaryKey()) == null) {
				cacheResult(replicon);
			}
			else {
				replicon.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all replicons.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RepliconImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the replicon.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Replicon replicon) {
		entityCache.removeResult(RepliconModelImpl.ENTITY_CACHE_ENABLED,
			RepliconImpl.class, replicon.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((RepliconModelImpl)replicon, true);
	}

	@Override
	public void clearCache(List<Replicon> replicons) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Replicon replicon : replicons) {
			entityCache.removeResult(RepliconModelImpl.ENTITY_CACHE_ENABLED,
				RepliconImpl.class, replicon.getPrimaryKey());

			clearUniqueFindersCache((RepliconModelImpl)replicon, true);
		}
	}

	protected void cacheUniqueFindersCache(RepliconModelImpl repliconModelImpl) {
		Object[] args = new Object[] {
				repliconModelImpl.getUuid(), repliconModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			repliconModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		RepliconModelImpl repliconModelImpl, boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					repliconModelImpl.getUuid(), repliconModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((repliconModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					repliconModelImpl.getOriginalUuid(),
					repliconModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}
	}

	/**
	 * Creates a new replicon with the primary key. Does not add the replicon to the database.
	 *
	 * @param projectId the primary key for the new replicon
	 * @return the new replicon
	 */
	@Override
	public Replicon create(long projectId) {
		Replicon replicon = new RepliconImpl();

		replicon.setNew(true);
		replicon.setPrimaryKey(projectId);

		String uuid = PortalUUIDUtil.generate();

		replicon.setUuid(uuid);

		replicon.setCompanyId(companyProvider.getCompanyId());

		return replicon;
	}

	/**
	 * Removes the replicon with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param projectId the primary key of the replicon
	 * @return the replicon that was removed
	 * @throws NoSuchRepliconException if a replicon with the primary key could not be found
	 */
	@Override
	public Replicon remove(long projectId) throws NoSuchRepliconException {
		return remove((Serializable)projectId);
	}

	/**
	 * Removes the replicon with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the replicon
	 * @return the replicon that was removed
	 * @throws NoSuchRepliconException if a replicon with the primary key could not be found
	 */
	@Override
	public Replicon remove(Serializable primaryKey)
		throws NoSuchRepliconException {
		Session session = null;

		try {
			session = openSession();

			Replicon replicon = (Replicon)session.get(RepliconImpl.class,
					primaryKey);

			if (replicon == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRepliconException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(replicon);
		}
		catch (NoSuchRepliconException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected Replicon removeImpl(Replicon replicon) {
		replicon = toUnwrappedModel(replicon);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(replicon)) {
				replicon = (Replicon)session.get(RepliconImpl.class,
						replicon.getPrimaryKeyObj());
			}

			if (replicon != null) {
				session.delete(replicon);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (replicon != null) {
			clearCache(replicon);
		}

		return replicon;
	}

	@Override
	public Replicon updateImpl(Replicon replicon) {
		replicon = toUnwrappedModel(replicon);

		boolean isNew = replicon.isNew();

		RepliconModelImpl repliconModelImpl = (RepliconModelImpl)replicon;

		if (Validator.isNull(replicon.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			replicon.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (replicon.getCreateDate() == null)) {
			if (serviceContext == null) {
				replicon.setCreateDate(now);
			}
			else {
				replicon.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!repliconModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				replicon.setModifiedDate(now);
			}
			else {
				replicon.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (replicon.isNew()) {
				session.save(replicon);

				replicon.setNew(false);
			}
			else {
				replicon = (Replicon)session.merge(replicon);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !RepliconModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((repliconModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { repliconModelImpl.getOriginalUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { repliconModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((repliconModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						repliconModelImpl.getOriginalUuid(),
						repliconModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						repliconModelImpl.getUuid(),
						repliconModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((repliconModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PROJECTNAME.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						repliconModelImpl.getOriginalProjectName()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_PROJECTNAME, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PROJECTNAME,
					args);

				args = new Object[] { repliconModelImpl.getProjectName() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_PROJECTNAME, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PROJECTNAME,
					args);
			}
		}

		entityCache.putResult(RepliconModelImpl.ENTITY_CACHE_ENABLED,
			RepliconImpl.class, replicon.getPrimaryKey(), replicon, false);

		clearUniqueFindersCache(repliconModelImpl, false);
		cacheUniqueFindersCache(repliconModelImpl);

		replicon.resetOriginalValues();

		return replicon;
	}

	protected Replicon toUnwrappedModel(Replicon replicon) {
		if (replicon instanceof RepliconImpl) {
			return replicon;
		}

		RepliconImpl repliconImpl = new RepliconImpl();

		repliconImpl.setNew(replicon.isNew());
		repliconImpl.setPrimaryKey(replicon.getPrimaryKey());

		repliconImpl.setUuid(replicon.getUuid());
		repliconImpl.setProjectId(replicon.getProjectId());
		repliconImpl.setGroupId(replicon.getGroupId());
		repliconImpl.setCompanyId(replicon.getCompanyId());
		repliconImpl.setUserId(replicon.getUserId());
		repliconImpl.setUserName(replicon.getUserName());
		repliconImpl.setCreateDate(replicon.getCreateDate());
		repliconImpl.setModifiedDate(replicon.getModifiedDate());
		repliconImpl.setProjectName(replicon.getProjectName());
		repliconImpl.setStartTime(replicon.getStartTime());
		repliconImpl.setEndTime(replicon.getEndTime());
		repliconImpl.setBilling(replicon.getBilling());
		repliconImpl.setActivity(replicon.getActivity());

		return repliconImpl;
	}

	/**
	 * Returns the replicon with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the replicon
	 * @return the replicon
	 * @throws NoSuchRepliconException if a replicon with the primary key could not be found
	 */
	@Override
	public Replicon findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRepliconException {
		Replicon replicon = fetchByPrimaryKey(primaryKey);

		if (replicon == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRepliconException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return replicon;
	}

	/**
	 * Returns the replicon with the primary key or throws a {@link NoSuchRepliconException} if it could not be found.
	 *
	 * @param projectId the primary key of the replicon
	 * @return the replicon
	 * @throws NoSuchRepliconException if a replicon with the primary key could not be found
	 */
	@Override
	public Replicon findByPrimaryKey(long projectId)
		throws NoSuchRepliconException {
		return findByPrimaryKey((Serializable)projectId);
	}

	/**
	 * Returns the replicon with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the replicon
	 * @return the replicon, or <code>null</code> if a replicon with the primary key could not be found
	 */
	@Override
	public Replicon fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(RepliconModelImpl.ENTITY_CACHE_ENABLED,
				RepliconImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		Replicon replicon = (Replicon)serializable;

		if (replicon == null) {
			Session session = null;

			try {
				session = openSession();

				replicon = (Replicon)session.get(RepliconImpl.class, primaryKey);

				if (replicon != null) {
					cacheResult(replicon);
				}
				else {
					entityCache.putResult(RepliconModelImpl.ENTITY_CACHE_ENABLED,
						RepliconImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(RepliconModelImpl.ENTITY_CACHE_ENABLED,
					RepliconImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return replicon;
	}

	/**
	 * Returns the replicon with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param projectId the primary key of the replicon
	 * @return the replicon, or <code>null</code> if a replicon with the primary key could not be found
	 */
	@Override
	public Replicon fetchByPrimaryKey(long projectId) {
		return fetchByPrimaryKey((Serializable)projectId);
	}

	@Override
	public Map<Serializable, Replicon> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, Replicon> map = new HashMap<Serializable, Replicon>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			Replicon replicon = fetchByPrimaryKey(primaryKey);

			if (replicon != null) {
				map.put(primaryKey, replicon);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(RepliconModelImpl.ENTITY_CACHE_ENABLED,
					RepliconImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (Replicon)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_REPLICON_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (Replicon replicon : (List<Replicon>)q.list()) {
				map.put(replicon.getPrimaryKeyObj(), replicon);

				cacheResult(replicon);

				uncachedPrimaryKeys.remove(replicon.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(RepliconModelImpl.ENTITY_CACHE_ENABLED,
					RepliconImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the replicons.
	 *
	 * @return the replicons
	 */
	@Override
	public List<Replicon> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the replicons.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepliconModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of replicons
	 * @param end the upper bound of the range of replicons (not inclusive)
	 * @return the range of replicons
	 */
	@Override
	public List<Replicon> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the replicons.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepliconModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of replicons
	 * @param end the upper bound of the range of replicons (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of replicons
	 */
	@Override
	public List<Replicon> findAll(int start, int end,
		OrderByComparator<Replicon> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the replicons.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepliconModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of replicons
	 * @param end the upper bound of the range of replicons (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of replicons
	 */
	@Override
	public List<Replicon> findAll(int start, int end,
		OrderByComparator<Replicon> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<Replicon> list = null;

		if (retrieveFromCache) {
			list = (List<Replicon>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_REPLICON);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_REPLICON;

				if (pagination) {
					sql = sql.concat(RepliconModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Replicon>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Replicon>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the replicons from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Replicon replicon : findAll()) {
			remove(replicon);
		}
	}

	/**
	 * Returns the number of replicons.
	 *
	 * @return the number of replicons
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_REPLICON);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return RepliconModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the replicon persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(RepliconImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_REPLICON = "SELECT replicon FROM Replicon replicon";
	private static final String _SQL_SELECT_REPLICON_WHERE_PKS_IN = "SELECT replicon FROM Replicon replicon WHERE projectId IN (";
	private static final String _SQL_SELECT_REPLICON_WHERE = "SELECT replicon FROM Replicon replicon WHERE ";
	private static final String _SQL_COUNT_REPLICON = "SELECT COUNT(replicon) FROM Replicon replicon";
	private static final String _SQL_COUNT_REPLICON_WHERE = "SELECT COUNT(replicon) FROM Replicon replicon WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "replicon.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Replicon exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Replicon exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(RepliconPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}