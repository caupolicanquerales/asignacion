package com.capo.redisVersion2.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.redisson.api.ObjectListener;
import org.redisson.api.RLockReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RPermitExpirableSemaphoreReactive;
import org.redisson.api.RReadWriteLockReactive;
import org.redisson.api.RSemaphoreReactive;
import org.redisson.client.codec.Codec;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MapReactiveMock implements RMapReactive<String,String>{

	@Override
	public Mono<Boolean> expire(long timeToLive, TimeUnit timeUnit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> expireAt(Date timestamp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> expireAt(long timestamp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> expire(Instant instant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> expireIfSet(Instant time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> expireIfNotSet(Instant time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> expireIfGreater(Instant time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> expireIfLess(Instant time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> expire(Duration duration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> expireIfSet(Duration duration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> expireIfNotSet(Duration duration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> expireIfGreater(Duration duration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> expireIfLess(Duration duration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> clearExpire() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Long> remainTimeToLive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Long> getExpireTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Long> getIdleTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Codec getCodec() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Long> sizeInMemory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Void> restore(byte[] state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Void> restore(byte[] state, long timeToLive, TimeUnit timeUnit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Void> restoreAndReplace(byte[] state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Void> restoreAndReplace(byte[] state, long timeToLive, TimeUnit timeUnit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<byte[]> dump() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> touch() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> unlink() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Void> copy(String host, int port, int database, long timeout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> copy(String destination) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> copy(String destination, int database) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> copyAndReplace(String destination) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> copyAndReplace(String destination, int database) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Void> migrate(String host, int port, int database, long timeout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> move(int database) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Void> rename(String newName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> renamenx(String newName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> isExists() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Void> removeListener(int listenerId) {
		return null;
	}

	@Override
	public Mono<String> merge(String key, String value,
			BiFunction<? super String, ? super String, ? extends String> remappingFunction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<String> compute(String key,
			BiFunction<? super String, ? super String, ? extends String> remappingFunction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<String> computeIfAbsent(String key, Function<? super String, ? extends String> mappingFunction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<String> computeIfPresent(String key,
			BiFunction<? super String, ? super String, ? extends String> remappingFunction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Void> loadAll(boolean replaceExistingValues, int parallelism) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Void> loadAll(Set<? extends String> keys, boolean replaceExistingValues, int parallelism) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Integer> valueSize(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Map<String, String>> getAll(Set<String> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Void> putAll(Map<? extends String, ? extends String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<String> addAndGet(String key, Number delta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> containsValue(Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> containsKey(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Integer> size() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Long> fastRemove(String... keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> fastPut(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> fastPutIfAbsent(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Set<String>> readAllKeySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Collection<String>> readAllValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Set<Entry<String, String>>> readAllEntrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Map<String, String>> readAllMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<String> get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<String> put(String key, String value) {
		return Mono.just("OK");
	}

	@Override
	public Mono<String> remove(String key) {
		return Mono.just("OK");
	}

	@Override
	public Mono<String> replace(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> replace(String key, String oldValue, String newValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> remove(Object key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<String> putIfAbsent(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<String> putIfExists(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Set<String>> randomKeys(int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Map<String, String>> randomEntries(int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Boolean> fastPutIfExists(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<Entry<String, String>> entryIterator() {
		Map.Entry<String, String> entry = Map.entry("key", "value");
		return Flux.just(entry);
	}

	@Override
	public Flux<Entry<String, String>> entryIterator(int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<Entry<String, String>> entryIterator(String pattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<Entry<String, String>> entryIterator(String pattern, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<String> valueIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<String> valueIterator(int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<String> valueIterator(String pattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<String> valueIterator(String pattern, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<String> keyIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<String> keyIterator(int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<String> keyIterator(String pattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<String> keyIterator(String pattern, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RPermitExpirableSemaphoreReactive getPermitExpirableSemaphore(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RSemaphoreReactive getSemaphore(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RLockReactive getFairLock(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RReadWriteLockReactive getReadWriteLock(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RLockReactive getLock(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Integer> addListener(ObjectListener listener) {
		// TODO Auto-generated method stub
		return null;
	}

}
