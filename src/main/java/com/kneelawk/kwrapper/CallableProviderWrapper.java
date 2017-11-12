package com.kneelawk.kwrapper;

import java.util.concurrent.Callable;

import org.gradle.api.provider.Provider;

public class CallableProviderWrapper<V> implements Callable<V> {

	private Provider<V> prov;

	public CallableProviderWrapper(Provider<V> prov) {
		this.prov = prov;
	}

	@Override
	public V call() throws Exception {
		return prov.get();
	}

}
