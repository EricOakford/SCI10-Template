;;; Sierra Script 1.0 - (do not remove this comment)
(script# 962)
(include sci.sh)

(public
	proc962_0 0
)

(procedure (proc962_0 param1 param2 param3 param4 &tmp temp0 temp1 temp2 temp3)
	(asm
		lap      param1
		sat      temp0
		ldi      0
		sat      temp1
		ldi      0
		sat      temp2
		lsp      argc
		ldi      3
		ge?     
		bnt      code_004b
		lap      param3
		sat      temp1
		lsp      argc
		ldi      4
		ge?     
		bnt      code_0034
		lap      param4
		sat      temp2
code_0034:
		pushi    #caller
		pushi    1
		lst      temp1
		pushi    135
		pushi    1
		lst      temp2
		pushi    1
		lsp      param2
		call     localproc_0178,  2
		sap      param2
		send     12
code_004b:
		ldi      1
		bnt      code_0177
		lat      temp0
		not     
		bnt      code_006c
		pushi    #setScript
		pushi    1
		pushi    1
		lsp      param2
		call     localproc_0178,  2
		push    
		lap      param1
		send     6
		jmp      code_0177
		jmp      code_004b
code_006c:
		pushi    #respondsTo
		pushi    1
		pushi    68
		lat      temp0
		send     6
		not     
		bnt      code_00a7
		pushi    #script
		pushi    0
		lat      temp0
		send     4
		bnt      code_0091
		pushi    #script
		pushi    0
		lat      temp0
		send     4
		sat      temp0
		jmp      code_004b
code_0091:
		pushi    #setScript
		pushi    1
		pushi    1
		lsp      param2
		call     localproc_0178,  2
		push    
		lat      temp0
		send     6
		jmp      code_0177
		jmp      code_004b
code_00a7:
		lst      temp0
		lap      param1
		eq?     
		bnt      code_00d0
		pushi    #script
		pushi    0
		lap      param1
		send     4
		sat      temp0
		push    
		pushi    #script
		pushi    0
		send     4
		eq?     
		bnt      code_004b
		pushi    #new
		pushi    0
		lat      temp0
		send     4
		sat      temp0
		jmp      code_004b
code_00d0:
		pushi    #next
		pushi    0
		lat      temp0
		send     4
		bnt      code_013f
		pushi    1
		pushi    #next
		pushi    0
		lat      temp0
		send     4
		push    
		call     localproc_0178,  2
		sat      temp3
		pushi    68
		pushi    1
		lst      temp0
		pushi    #next
		pushi    0
		lat      temp0
		send     4
		eq?     
		bnt      code_010c
		pushi    #next
		pushi    1
		pushi    0
		pushi    114
		pushi    0
		pushi    #new
		pushi    0
		lat      temp3
		send     4
		send     10
		jmp      code_012e
code_010c:
		pushi    #-info-
		pushi    0
		lat      temp0
		send     4
		push    
		ldi      1
		and     
		bnt      code_012c
		pushi    #next
		pushi    1
		pushi    0
		pushi    114
		pushi    0
		pushi    #new
		pushi    0
		lat      temp3
		send     4
		send     10
		jmp      code_012e
code_012c:
		lat      temp3
code_012e:
		push    
		lat      temp0
		send     6
		pushi    #next
		pushi    0
		lat      temp0
		send     4
		sat      temp0
		jmp      code_004b
code_013f:
		pushi    68
		pushi    1
		lsp      param2
		lat      temp0
		eq?     
		bt       code_0160
		pushi    1
		lsp      param2
		callk    IsObject,  2
		bnt      code_016a
		pushi    #-info-
		pushi    0
		lap      param2
		send     4
		push    
		ldi      1
		and     
		bnt      code_016a
code_0160:
		pushi    #new
		pushi    0
		lap      param2
		send     4
		jmp      code_016c
code_016a:
		lap      param2
code_016c:
		push    
		lat      temp0
		send     6
		jmp      code_0177
		jmp      code_004b
code_0177:
		ret     
	)
)

(procedure (localproc_0178 param1)
	(if (IsObject param1) param1 else (ScriptID param1))
)
