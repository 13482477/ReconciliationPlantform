package com.yonyou.reconciliation.resource.converter;

import org.springframework.core.convert.converter.Converter;

import com.yonyou.reconciliation.resource.dto.ResourceDTO;
import com.yonyou.reconciliation.resource.entity.Resource;

public class ResourceToResourceDTOConverter implements Converter<Resource, ResourceDTO> {

	@Override
	public ResourceDTO convert(Resource source) {
		return null;
	}

}
