;;; Sierra Script 1.0 - (do not remove this comment)\
;********************************************************************
;***
;***	GRAPHICS.SH
;***	  Defines for views, pictures, and cursors
;***
;********************************************************************

;
; Ego views
(define	vEgo		0)
(define vEgoStand	1)
(define vEgoTalk	10)

;
; Speed tester
(define vSpeedTest 99)

;
; Inventory items
(define vInvItems	700)

;
; Death icons
(define vDeathSkull 800)

;
; Interface views
(define vIcons	900)
	(enum
		lIconWalk
		lIconLook
		lIconHand
		lIconTalk
		lIconInvItem
		lIconInventory
		lIconExit
		lIconControls
		lIconScore
		lIconHelp
		lIconDisabled
	)	
(define vInvIcons 901)
	(enum
		lInvHand
		lInvHelp
		lInvLook
		lInvOK
		lInvSelect
	)	
(define vGameControls			947)
(define vGameControlsFrench		948)
(define vGameControlsSpanish	949)
(define vGameControlsItalian	950)
(define vGameControlsGerman		951)
(define vGameControlsJapanese	952)
(define vGameControlsPortuguese	953)	
	(enum
		lSliderText
		lControlFixtures
		lSaveButton
		lRestoreButton
		lRestartButton
		lQuitButton
		lAboutButton
		lHelpButton
		lOKButton
	)

;
; Picture defines
(define pTestRoom 110)

;
; Cursors
(define WALK_CURSOR 100)
(define LOOK_CURSOR 101)
(define DO_CURSOR	102)
(define TALK_CURSOR 103)
(define HELP_CURSOR 104)
(define WAIT_CURSOR	105)