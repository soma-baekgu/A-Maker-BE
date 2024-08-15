package com.backgu.amaker.batch.component

import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.item.database.AbstractPagingItemReader
import org.springframework.dao.DataAccessResourceFailureException
import org.springframework.util.ClassUtils
import org.springframework.util.CollectionUtils
import java.util.concurrent.CopyOnWriteArrayList
import java.util.function.Function

class QuerydslPagingItemReader<T>(
    private val entityManagerFactory: EntityManagerFactory,
    pageSize: Int,
    private val queryFunction: Function<JPAQueryFactory, JPAQuery<T>>,
) : AbstractPagingItemReader<T>() {
    private val jpaPropertyMap: MutableMap<String, Any> = HashMap()
    private var entityManager: EntityManager? = null
    private var transacted = true

    init {
        name = ClassUtils.getShortName(QuerydslPagingItemReader::class.java)
        setPageSize(pageSize)
    }

    fun setTransacted(transacted: Boolean) {
        this.transacted = transacted
    }

    override fun doOpen() {
        super.doOpen()
        entityManager = entityManagerFactory.createEntityManager(jpaPropertyMap)
        if (entityManager == null) {
            throw DataAccessResourceFailureException("Unable to obtain an EntityManager")
        }
    }

    override fun doReadPage() {
        clearIfTransacted()

        val query =
            createQuery()
                .offset((page * pageSize).toLong())
                .limit(pageSize.toLong())

        initResults()
        fetchQuery(query)
    }

    private fun clearIfTransacted() {
        if (transacted) {
            entityManager?.clear()
        }
    }

    private fun createQuery(): JPAQuery<T> {
        val queryFactory = JPAQueryFactory(entityManager)
        return queryFunction.apply(queryFactory)
    }

    private fun initResults() {
        if (CollectionUtils.isEmpty(results)) {
            results = CopyOnWriteArrayList()
        } else {
            results.clear()
        }
    }

    private fun fetchQuery(query: JPAQuery<T>) {
        if (!transacted) {
            val queryResult = query.fetch()
            for (entity in queryResult) {
                entityManager?.detach(entity)
                results.add(entity)
            }
        } else {
            results.addAll(query.fetch())
        }
    }

    override fun doClose() {
        entityManager?.close()
        super.doClose()
    }
}
