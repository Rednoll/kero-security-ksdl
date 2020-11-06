package com.kero.security.ksdl.extractor.p1;

import com.kero.security.core.access.annotations.DefaultDeny;
import com.kero.security.core.access.annotations.DefaultGrant;
import com.kero.security.core.access.annotations.DenyFor;
import com.kero.security.core.access.annotations.GrantFor;
import com.kero.security.core.role.annotations.PropagateRole;

@DefaultDeny
public class Root {

	@DefaultGrant
	@GrantFor("OWNER")
	@DenyFor("FRIEND")
	@PropagateRole(from = "OWNER", to = "FRIEND")
	private String name;
}
