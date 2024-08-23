LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-vilmursss.git;protocol=ssh;branch=master"

PV = "1.0+git${SRCPV}"

SRCREV = "58a55a62fce5840dfb5dd3d80caa4980ac875b1e"

S = "${WORKDIR}/git/misc-modules"

inherit module

# Append to EXTRA_OEMAKE for the do_compile task
EXTRA_OEMAKE:append:task-compile = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

FILES:${PN} += "${bindir}/module_load ${bindir}/module_unload"

do_configure() {
    :
}

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/module_load ${D}${bindir}/module_load
    install -m 0755 ${S}/module_load ${D}${bindir}/module_unload

    # Install device driver
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0755 ${S}/faulty.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0755 ${S}/hello.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/
}
